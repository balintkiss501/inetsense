package hu.elte.inetsense.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import hu.elte.inetsense.util.JsonValidationException;
import hu.elte.inetsense.web.dtos.ProbeDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service for the Standardized Interface: validation of incoming JSON data against existing JSON schema.
 * <p>
 * Created by balintkiss on 3/22/16.
 */
@Component
public class JsonValidator {

    private final Logger log = LoggerFactory.getLogger(JsonValidator.class);
    private final String schemaFileStr = "src/main/resources/probe-validation.schema.json";

    private String schemaStr = "";
    private ObjectMapper mapper;
    private JsonSchema jsonSchema;

    /**
     * Initialize validator service.
     */
    public JsonValidator() throws JsonValidationException {

        // Load schema file
        String schemaFilePath = new File(schemaFileStr).getAbsolutePath();
        try {
            schemaStr = new String(Files.readAllBytes(Paths.get(schemaFilePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error when accessing JSON schema file for Validator.", e);
            throw new JsonValidationException("Error when accessing JSON schema file for Validator.");
        }

        // Parse schema file as simple JSON data
        JsonNode schemaNode;
        try {
            schemaNode = JsonLoader.fromString(schemaStr);
        } catch (IOException e) {
            log.error("Error when parsing JSON schema. Make sure the scema file is in valid JSON format. " +
                    "Use a linter: http://jsonschemalint.com/draft4/#", e);
            throw new JsonValidationException("Error when parsing JSON schema. Make sure the schema file is in valid JSON format.");
        }

        // Process JSON data as valid JSON schema
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        try {
            jsonSchema = factory.getJsonSchema(schemaNode);
        } catch (ProcessingException e) {
            log.error("Error when processing JSON schema. Make sure the schema file is in valid schema format. " +
                    "Use a linter: http://jsonschemalint.com/draft4/#", e);
            throw new JsonValidationException("Error when processing JSON schema. Make sure the schema file is in valid schema format." );
        }

        // Return JSON object mapper
        mapper = new ObjectMapper();
    }

    /**
     * Validate incoming JSON message object.
     *
     * @param message
     * @return
     */
    public ProbeDataDTO validate(String message) throws JsonValidationException {

        // Nullcheck for incoming message string
        if (null == message || message.isEmpty()) {
            log.error("Message is empty.");
            throw new JsonValidationException("Message is empty.");
        }

        // Convert message to JSON object
        JsonNode messageNode;
        try {
            messageNode = JsonLoader.fromString(message);
        } catch (IOException e) {
            log.error("Incoming message is not even in valid JSON format:\n" + message);
            throw new JsonValidationException("Incoming message is not even in valid JSON format.");
        }

        /**
         * TODO: Better error handling, which might be difficult to implement.
         */
        // Validate JSON object to schema
        ProcessingReport report;
        try {
            report = jsonSchema.validate(messageNode);
            if (report.isSuccess()) {
                try {
                    ProbeDataDTO probeDataDTO = mapper.treeToValue(messageNode, ProbeDataDTO.class);
                    log.info("Incoming message validation was successful.");
                    return probeDataDTO;
                } catch (JsonProcessingException e) {
                    log.error("Error when mapping JSON to Java class:\n" + mapper.writeValueAsString(messageNode), e);
                    throw new JsonValidationException("Error when mapping JSON to Java class.");
                }
            } else {
                log.error("Incoming message does not conform to schema:\n" + mapper.writeValueAsString(messageNode));
                throw new JsonValidationException("Incoming message does not conform to schema.");
            }
        } catch (ProcessingException | JsonProcessingException e) {
            log.error("Error when processing validation schema on host side.", e);
            throw new JsonValidationException("Error when processing validation schema on host side.");
        }
    }
}
