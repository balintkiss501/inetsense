package hu.elte.inetsense;

import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.service.ProbeDataService;
import hu.elte.inetsense.web.JsonValidatorController;
import hu.elte.inetsense.web.dtos.MeasurementDTO;
import hu.elte.inetsense.web.dtos.ProbeDataDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for the JSON validator service
 * <p>
 * Created by balintkiss on 3/22/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = JsonValidatorTest.class)
public class JsonValidatorTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JsonValidator validator;

    private MockMvc mockMvc;

    @Mock
    private JsonValidator validatorMock;

    @Mock
    private ProbeDataService probeDataServiceMock;

    @InjectMocks
    private JsonValidatorController controllerMock;

    private String validJsonString;
    private ProbeDataDTO fullProbeData;


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
        // Not even a JSON text
        ProbeDataDTO notEvenJson = validator.validate("Bogus String");
        assertNull(notEvenJson);

        // Invalid JSON
        ProbeDataDTO invalidObject = validator.validate("{\"invalid_field\":\"invalid_value\"}");
        assertNull(invalidObject);

    }

    @Test
    public void testValidatorController() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerMock).build();

        when(validatorMock.validate(anyString())).thenReturn(null);
        when(validatorMock.validate(validJsonString)).thenReturn(fullProbeData);

        try {
            mockMvc.perform(post("/message-endpoint")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(validJsonString))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/message-endpoint")
                    .content("{\"invalid_field\":\"invalid_value\"}"))
                    .andExpect(status().isInternalServerError());

            mockMvc.perform(post("/message-endpoint")
                    .content("Bogus String"))
                    .andExpect(status().isInternalServerError());
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
