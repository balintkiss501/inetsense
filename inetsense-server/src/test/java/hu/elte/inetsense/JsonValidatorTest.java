package hu.elte.inetsense;

import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.web.dtos.JsonMessageObject;
import hu.elte.inetsense.web.dtos.MeasurementDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Unit test for the JSON validator service
 *
 * Created by balintkiss on 3/22/16.
 */

/**
 * TODO: Context configuration should be either separate Bean configuration class, or (better yet) App.class, which
 * has annotated component scanning via @SpringBootApplication,
 * but ApplicationContext loading fails because of failing dependency autowiring in MeasurementServiceImpl.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JsonValidatorTest.class)
public class JsonValidatorTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JsonValidator validator;

    @Test
    public void testSimpleValidJson() {
        String plainIdString = "{\"id\": 1}";
        JsonMessageObject messageObject = new JsonMessageObject();
        messageObject.setId(1L);
        messageObject.setLat(0.0F);
        messageObject.setIon(9999.0F);
        JsonMessageObject validatedObject = validator.validate(plainIdString);

        assertEquals(messageObject.getId(), validatedObject.getId());
    }

    @Test
    public void testWholeValidJson() {

        // Read JSON file
        String validJsonString = "";
        String validJsonPath = new File("src/test/resources/valid-testdata.json").getAbsolutePath();
        try {
            validJsonString = new String(Files.readAllBytes(Paths.get(validJsonPath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.info("Error when accessing file.");
            e.printStackTrace();
        }

        // Create test measurement data
        List<MeasurementDTO> measurements = new ArrayList<>();
        Calendar c = Calendar.getInstance();

        MeasurementDTO measurement1 = new MeasurementDTO();
        measurement1.setId(1L);
        c.setTimeInMillis(1455458400000L);
        measurement1.setCompletedOn(c.getTime());
        measurement1.setUploadSpeed(20L);
        measurement1.setDownloadSpeed(20L);

        MeasurementDTO measurement2 = new MeasurementDTO();
        measurement2.setId(2L);
        c.setTimeInMillis(1455458400000L);
        measurement2.setCompletedOn(c.getTime());
        measurement2.setUploadSpeed(200L);
        measurement2.setDownloadSpeed(200L);

        measurements.add(measurement1);

        // Create test
        JsonMessageObject messageObject = new JsonMessageObject();
        messageObject.setId(1L);
        messageObject.setLat(0.0F);
        messageObject.setIon(9999.0F);
        messageObject.setMeasurements(measurements);

        // Validate JSON
        JsonMessageObject validatedObject = validator.validate(validJsonString);

        // Assert cases
        assertEquals(messageObject.getId(), validatedObject.getId());
        assertEquals(messageObject.getLat(), validatedObject.getLat());
        assertEquals(messageObject.getIon(), validatedObject.getIon());

        for ( MeasurementDTO m : measurements ) {
            assertEquals(m.getId(), validatedObject.getMeasurements().get(measurements.indexOf(m)).getId());
            assertEquals(m.getCompletedOn(),
                    validatedObject.getMeasurements().get(measurements.indexOf(m)).getCompletedOn());
            assertEquals(m.getDownloadSpeed(),
                    validatedObject.getMeasurements().get(measurements.indexOf(m)).getDownloadSpeed());
            assertEquals(m.getUploadSpeed(),
                    validatedObject.getMeasurements().get(measurements.indexOf(m)).getUploadSpeed());
        }
    }

    /**
     * TODO: Bogus test case, unfortunately validator creates the object fine, failure not implemented yet.
     */
    @Test
    public void testInvalidJson() {
        JsonMessageObject invalidObject = validator.validate("{\"invalidfield\":\"invalid_value\"}");

        fail();
    }

    @Bean
    public JsonValidator getJsonValidatorBean() {
        return new JsonValidator();
    }
}
