package hu.elte.inetsense;

import hu.elte.inetsense.domain.entities.Measurement;
import hu.elte.inetsense.web.dtos.JsonMessageObject;
import hu.elte.inetsense.service.JsonValidator;

import hu.elte.inetsense.web.dtos.MeasurementDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        JsonMessageObject validatedObject = validator.validate(plainIdString);

        assertEquals(messageObject.getId(), validatedObject.getId());
    }

    /**
     * TODO: Array of measurement data is not implemented yet.
     */
    @Test
    public void testWholeValidJson() {
        String validJsonString = "";
        String validJsonPath = new File("src/test/resources/valid-testdata.json").getAbsolutePath();
        try {
            validJsonString = new String(Files.readAllBytes(Paths.get(validJsonPath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.info("Error when accessing file.");
            e.printStackTrace();
        }

        List<MeasurementDTO> measurements = new ArrayList<>();
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setId(1L);
        Calendar c = Calendar.getInstance();
        //c.set(2016, Calendar.FEBRUARY, 14, 15, 0, 0);
        c.setTimeInMillis(1455458400000L);
        measurement.setCompletedOn(c.getTime());
        measurement.setUploadSpeed(20L);
        measurement.setDownloadSpeed(20L);
        measurements.add(measurement);

        JsonMessageObject messageObject = new JsonMessageObject();
        messageObject.setId(1L);
        messageObject.setLat(0.0F);
        messageObject.setIon(9999.0F);
        messageObject.setMeasurements(measurements);

        JsonMessageObject validatedObject = validator.validate(validJsonString);

        boolean etwas = (messageObject.getMeasurements().get(0).getCompletedOn()
                .equals(validatedObject.getMeasurements().get(0).getCompletedOn()));

        assertEquals(messageObject.getId(), validatedObject.getId());
        assertEquals(messageObject.getMeasurements().get(0).getId(),
                validatedObject.getMeasurements().get(0).getId());
        assertTrue(messageObject.getMeasurements().get(0).getCompletedOn()
                .equals(validatedObject.getMeasurements().get(0).getCompletedOn()));
        assertEquals(messageObject.getMeasurements().get(0).getDownloadSpeed(),
                validatedObject.getMeasurements().get(0).getDownloadSpeed());
        assertEquals(messageObject.getMeasurements().get(0).getUploadSpeed(),
                validatedObject.getMeasurements().get(0).getUploadSpeed());
    }

    /**
     * TODO: Bogus test case, unfortunately validator creates the object fine, failure not implemented yet.
     */
    @Test
    public void testInvalidJson() {
        JsonMessageObject invalidObject = validator.validate("{\"invalidfield\":\"invalid_value\"}");

        assertTrue(true);
    }

    @Bean
    public JsonValidator getJsonValidatorBean() {
        return new JsonValidator();
    }
}
