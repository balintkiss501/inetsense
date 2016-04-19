package hu.elte.inetsense.server.collector.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service for the Standardized Interface: validation of incoming JSON data against existing JSON schema
 *
 * Created by balintkiss on 3/22/16.
 */
@Component
public class JsonValidator {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final String schemaFileStr = "src/main/resources/probe-validation.schema.json";

    private String schemaStr = "";
    private ObjectMapper mapper;
    private JsonSchema jsonSchema;

    /**
     * TODO: Ugly, undocumented, but works
     * Also, I don't like returning null object, but I was in a hurry.
     */
    public JsonValidator() {
        String schemaFilePath = new File(schemaFileStr).getAbsolutePath();
        try {
            schemaStr = new String(Files.readAllBytes(Paths.get(schemaFilePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error when accessing JSON schema file for Validator.", e);
        }

        JsonNode schemaNode = null;
        try {
            schemaNode = JsonLoader.fromString(schemaStr);
        } catch (IOException e) {
            log.error("Error when parsing JSON schema. Make sure, that it is in valid JSON format. " +
                    "Use a linter: http://jsonschemalint.com/draft4/#", e);
        }

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

        try {
            jsonSchema = factory.getJsonSchema(schemaNode);
        } catch (ProcessingException e) {
            log.error("Error when processing JSON schema. Make sure, that it is a valid schema format. " +
                    "Use a linter: http://jsonschemalint.com/draft4/#", e);
        }

        mapper = new ObjectMapper();
    }

    public ProbeDataDTO validate(String message) {

        JsonNode messageNode;

        if (null == message || message.isEmpty()) {
            log.error("Message is empty!");
            return null;
        }

        try {
            messageNode = JsonLoader.fromString(message) ;
        } catch (IOException e) {
            log.error("Incoming message is not even in valid JSON format!", e);
            return null;
        }

        ProcessingReport report;
        try {
            report = jsonSchema.validate(messageNode);
            if (report.isSuccess()) {
                try {
                    return mapper.treeToValue(messageNode, ProbeDataDTO.class);
                } catch (JsonProcessingException e) {
                    log.error("Error when mapping JSON to Java class", e);
                }
            } else {
                log.error("Incoming message does not conform to schema");
            }
        } catch (ProcessingException e) {
            log.error("Error when processing validation schema.", e);
        }

        return null;
    }
}
