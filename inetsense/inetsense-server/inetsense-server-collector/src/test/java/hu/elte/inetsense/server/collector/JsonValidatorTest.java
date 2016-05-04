package hu.elte.inetsense.server.collector;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.controller.JsonValidatorController;
import hu.elte.inetsense.server.collector.service.JsonValidator;
import hu.elte.inetsense.server.collector.service.ProbeDataService;
import hu.elte.inetsense.server.collector.util.JsonValidationException;
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

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
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

        try {
            String plainString = "{\"probeAuthId\": \"12345678\" }";
            ProbeDataDTO messageObject = new ProbeDataDTO();
            messageObject.setProbeAuthId("12345678");
            ProbeDataDTO validatedObject = validator.validate(plainString);

            assertEquals(messageObject.getProbeAuthId(), validatedObject.getProbeAuthId());
        } catch (JsonValidationException e) {
            log.error(e.getMessage(), e);
            fail("Test case is not supposed to throw exception.");
        }
    }

    @Test
    public void testWholeValidJson() {

        try {
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
        } catch (JsonValidationException e) {
            log.error(e.getMessage(), e);
            fail("Test case is not supposed to throw exception.");
        }
    }

    @Test
    public void testErrorCases() {

        // Null object
        try {
            validator.validate(null);
            fail("The test did not throw exception.");
        } catch (JsonValidationException e) {
            assertThat(e, instanceOf(JsonValidationException.class));
            assertEquals("Message is empty.", e.getMessage());
        }

        // Empty message
        try {
            validator.validate("");
            fail("The test did not throw exception.");
        } catch (JsonValidationException e) {
            assertThat(e, instanceOf(JsonValidationException.class));
            assertEquals("Message is empty.", e.getMessage());
        }

        // Not even a JSON text
        try {
            ProbeDataDTO notEvenJson = validator.validate("Bogus String");
        } catch (JsonValidationException e) {
            assertThat(e, instanceOf(JsonValidationException.class));
            assertEquals("Incoming message is not even in valid JSON format.", e.getMessage());
        }

        // Invalid JSON
        try {
            ProbeDataDTO invalidObject = validator.validate("{\"invalid_field\":\"invalid_value\"}");
        } catch (JsonValidationException e) {
            assertThat(e, instanceOf(JsonValidationException.class));
            assertEquals("Incoming message does not conform to schema.", e.getMessage());
        }
    }

    @Test
    public void testValidatorController() throws JsonValidationException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerMock).build();

        doNothing().when(probeDataServiceMock).saveProbeData(any());

        when(validatorMock.validate("Bogus String"))
                .thenThrow(new JsonValidationException("Incoming message is not even in valid JSON format."));
        when(validatorMock.validate("{\"invalid_field\":\"invalid_value\"}"))
                .thenThrow(new JsonValidationException("Incoming message does not conform to schema."));
        when(validatorMock.validate(validJsonString)).thenReturn(fullProbeData);

        try {
            mockMvc.perform(post("/message-endpoint")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(validJsonString))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/message-endpoint")
                    .content("Bogus String"))
                    .andExpect(status().isInternalServerError());

            mockMvc.perform(post("/message-endpoint")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"invalid_field\":\"invalid_value\"}"))
                    .andExpect(status().isInternalServerError());

        } catch (Exception e) {
            log.error("Error when verifying controller.", e);
            fail("Error when verifying controller.");
        }
    }

    /*
      This way, when executing the test individually, takes less time
      because it skips the DB context.
     */

    @Bean
    public JsonValidator getJsonValidator() {
        try {
            return new JsonValidator();
        } catch (JsonValidationException e) {
            log.error("Error creating validator.", e);
            return null;
        }
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
