package hu.elte.inetsense;

import hu.elte.inetsense.config.JsonValidatorConfig;
import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.web.dtos.MeasurementDTO;
import hu.elte.inetsense.web.dtos.ProbeDTO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for the JSON validator service
 * <p>
 * Created by balintkiss on 3/22/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JsonValidatorConfig.class)
public class JsonValidatorTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;

    @Autowired
    private JsonValidator validator;

    private String validJsonString;
    private ProbeDTO fullProbeData;

    @Before
    public void init() {
        // Read JSON file
        validJsonString = "";
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

        // Create test probe data
        fullProbeData = new ProbeDTO();
        fullProbeData.setProbeAuthId("12345678");
        fullProbeData.setMeasurements(measurements);
    }

    @Test
    public void testSimpleValidJson() {
        String plainString = "{\"probeAuthId\": \"12345678\" }";
        ProbeDTO messageObject = new ProbeDTO();
        messageObject.setProbeAuthId("12345678");
        ProbeDTO validatedObject = validator.validate(plainString);

        assertEquals(messageObject.getProbeAuthId(), validatedObject.getProbeAuthId());
    }

    @Test
    public void testWholeValidJson() {

        // Validate JSON
        ProbeDTO validatedObject = validator.validate(validJsonString);

        // Assert cases
        assertEquals(fullProbeData.getProbeAuthId(), validatedObject.getProbeAuthId());
        for (MeasurementDTO m : fullProbeData.getMeasurements()) {
            assertEquals(m.getLat(),
                    validatedObject.getMeasurements()
                            .get(fullProbeData.getMeasurements().indexOf(m)).getLat());
            assertEquals(m.getLng(),
                    validatedObject.getMeasurements()
                            .get(fullProbeData.getMeasurements().indexOf(m)).getLng());
            assertEquals(m.getCompletedOn(),
                    validatedObject.getMeasurements()
                            .get(fullProbeData.getMeasurements().indexOf(m)).getCompletedOn());
            assertEquals(m.getDownloadSpeed(),
                    validatedObject.getMeasurements()
                            .get(fullProbeData.getMeasurements().indexOf(m)).getDownloadSpeed());
            assertEquals(m.getUploadSpeed(),
                    validatedObject.getMeasurements()
                            .get(fullProbeData.getMeasurements().indexOf(m)).getUploadSpeed());
        }
    }

    @Test
    public void testInvalidJson() {
        // Invalid JSON
        ProbeDTO invalidObject = validator.validate("{\"invalid_field\":\"invalid_value\"}");
        assertNull(invalidObject);

        // Not even a JSON text
        ProbeDTO notEvenJson = validator.validate("Bogus String");
        assertNull(notEvenJson);
    }

    /**
     * TODO
     */
    @Ignore
    @Test
    public void testValidatorController() {
//        when(validator.validate(Matchers.any(String.class))).thenReturn(null);
        when(validator.validate(Matchers.eq(validJsonString))).thenReturn(fullProbeData);

        try {
            mockMvc.perform(post("/message-endpoint")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(validJsonString))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/message-endpoint")
                    .content("Bogus String"))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
