package hu.elte.inetsense;

import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.service.ProbeDataService;
import hu.elte.inetsense.service.ProbeDataServiceImpl;
import hu.elte.inetsense.web.JsonValidatorController;
import hu.elte.inetsense.web.dtos.MeasurementDTO;
import hu.elte.inetsense.web.dtos.ProbeDataDTO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for the JSON validator service
 *
 * Created by balintkiss on 3/22/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JsonValidatorTest.class)
public class JsonValidatorTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JsonValidator validator;

    private String validJsonString;
    private ProbeDataDTO fullProbeData;

    private MockMvc mockMvc;

    @Before
    public void init() {

        // Read JSON file
        validJsonString = "";
        String validJsonPath = new File("src/test/resources/valid-testdata.json").getAbsolutePath();
        try {
            validJsonString = new String(Files.readAllBytes(Paths.get(validJsonPath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error when accessing test data file.", e);
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
        fullProbeData = new ProbeDataDTO();
        fullProbeData.setProbeAuthId("12345678");
        fullProbeData.setMeasurements(measurements);
    }

    @Test
    public void testSimpleValidJson() {
        String plainString = "{\"probeAuthId\": \"12345678\" }";
        ProbeDataDTO messageObject = new ProbeDataDTO();
        messageObject.setProbeAuthId("12345678");
        ProbeDataDTO validatedObject = validator.validate(plainString);

        assertEquals(messageObject.getProbeAuthId(), validatedObject.getProbeAuthId());
    }

    @Test
    public void testWholeValidJson() {

        // Validate JSON
        ProbeDataDTO validatedObject = validator.validate(validJsonString);

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
        ProbeDataDTO invalidObject = validator.validate("{\"invalid_field\":\"invalid_value\"}");
        assertNull(invalidObject);

        // Not even a JSON text
        ProbeDataDTO notEvenJson = validator.validate("Bogus String");
        assertNull(notEvenJson);
    }

    /**
     * TODO: Was not able to mock yet, but it works live.
     *
     * curl -X POST -H "Content-Type: application/json"
     * -d @valid-testdata.json http://localhost:8080/message-endpoint
     */
    @Ignore
    @Test
    public void testValidatorController() {
        //mockMvc = MockMvcBuilders.standaloneSetup(new JsonValidatorController()).build();

        //when(validator.validate(anyString())).thenReturn(null);
        //doThrow(new RuntimeException()).when(validator).validate(anyObject());

        //when(validatorMock.validate(eq(validJsonString))).thenReturn(fullProbeData);

        try {
            mockMvc.perform(post("/message-endpoint")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("message", validJsonString))
                    .andExpect(status().isOk());
            //verify(validator).validate(eq(validJsonString));

            //mockMvc.perform(post("/message-endpoint")
            //        .content("Bogus String"))
            //        .andExpect(status().isInternalServerError());
            //verify(validator).validate(anyString());
        } catch (Exception e) {
            log.error("Error when verifying controller.", e);
        }
    }

    /*
      This way, when executing the test individually, takes less time
      because it skips the DB context.
     */

    @Bean
    public JsonValidator getJsonValidator() {
        return new JsonValidator();
    }

    @Bean
    public JsonValidatorController getJsonValidatorController() {
        return new JsonValidatorController();
    }
    
    @Bean
    public ProbeDataService getProbeDataService() {
        return new ProbeDataService() {

            @Override
            public void saveProbeData(ProbeDataDTO probeData) {
            }
        };
    }
    
}
