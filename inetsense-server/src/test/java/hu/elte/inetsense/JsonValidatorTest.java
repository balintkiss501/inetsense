package hu.elte.inetsense;

import hu.elte.inetsense.configuration.DozerMappingConfiguration;
import hu.elte.inetsense.domain.entities.JsonMessageObject;
import hu.elte.inetsense.service.JsonValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for the JSON validator service
 *
 * Created by balintkiss on 3/22/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DozerMappingConfiguration.class)
public class JsonValidatorTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * TODO: Please someone explain me why do we need Dozer and how to access my validator bean.
     * ApplicationContext in AppTest class initializes fine with the @ContextConfiguration.
     */
    //@Autowired
    private JsonValidator validator = new JsonValidator();

    @Test
    public void testSimpleValidJson() {
        JsonMessageObject messageObject = new JsonMessageObject(1);
        JsonMessageObject validatedObject = validator.validate("{\"probeId\":1}");

        assertEquals(messageObject.getProbeId(), validatedObject.getProbeId());
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

        JsonMessageObject messageObject = new JsonMessageObject(1);
        JsonMessageObject validatedObject = validator.validate(validJsonString);

        assertEquals(messageObject.getProbeId(), validatedObject.getProbeId());
    }

    /**
     * TODO: Bogus test case, unfortunately validator creates the object fine, failure not implemented yet.
     */
    @Test
    public void testInvalidJson() {
        JsonMessageObject invalidObject = validator.validate("{\"invalidfield\":\"invalid_value\"}");

        assertTrue(true);
    }
}
