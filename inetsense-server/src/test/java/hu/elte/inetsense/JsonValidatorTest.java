package hu.elte.inetsense;

import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.web.dtos.ProbeDTO;
import hu.elte.inetsense.web.dtos.MeasurementDTO;
import org.junit.Ignore;
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
        String plainString = "{\"probeAuthId\": \"12345678\"," +
                "\"lat\": 0.0," +
                "\"lon\": 9999.0 }";
        ProbeDTO messageObject = new ProbeDTO();
        messageObject.setProbeAuthId("12345678");
        ProbeDTO validatedObject = validator.validate(plainString);

        assertEquals(messageObject.getProbeAuthId(), validatedObject.getProbeAuthId());
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
        c.setTimeInMillis(1455458400000L);
        measurement1.setLat(0.0F);
        measurement1.setLng(9999.0F);
        measurement1.setCompletedOn(c.getTime());
        measurement1.setUploadSpeed(20L);
        measurement1.setDownloadSpeed(20L);

        MeasurementDTO measurement2 = new MeasurementDTO();
        c.setTimeInMillis(1455458400000L);
        measurement2.setCompletedOn(c.getTime());
        measurement2.setUploadSpeed(200L);
        measurement2.setDownloadSpeed(200L);

        measurements.add(measurement1);

        // Create test
        ProbeDTO messageObject = new ProbeDTO();
        messageObject.setProbeAuthId("12345678");
        messageObject.setMeasurements(measurements);

        // Validate JSON
        ProbeDTO validatedObject = validator.validate(validJsonString);

        // Assert cases
        assertEquals(messageObject.getProbeAuthId(), validatedObject.getProbeAuthId());

        for ( MeasurementDTO m : measurements ) {
            assertEquals(m.getLat(),
                    validatedObject.getMeasurements().get(measurements.indexOf(m)).getLat());
            assertEquals(m.getLng(),
                    validatedObject.getMeasurements().get(measurements.indexOf(m)).getLng());
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
    @Ignore
    @Test
    public void testInvalidJson() {
        ProbeDTO invalidObject = validator.validate("{\"invalidfield\":\"invalid_value\"}");

    }

    /**
     * TODO: Validator REST Controller is not ready yet.
     */
    @Ignore
    @Test
    public void testValidatorController() {

    }

    @Bean
    public JsonValidator getJsonValidatorBean() {
        return new JsonValidator();
    }
}
