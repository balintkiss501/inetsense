package hu.elte.inetsense.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import hu.elte.inetsense.web.dtos.ProbeDTO;
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
     */
    public JsonValidator() {
        String schemaFilePath = new File(schemaFileStr).getAbsolutePath();
        try {
            schemaStr = new String(Files.readAllBytes(Paths.get(schemaFilePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.info("Error when accessing JSON schema file for Validator.");
            e.printStackTrace();
        }

        JsonNode schemaNode = null;
        try {
            schemaNode = JsonLoader.fromString(schemaStr);
        } catch (IOException e) {
            log.info("Error when parsing JSON schema. Make sure, that it is in valid JSON format, use a linter: http://jsonschemalint.com/draft4/#");
            e.printStackTrace();
        }

        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

        try {
            jsonSchema = factory.getJsonSchema(schemaNode);
        } catch (ProcessingException e) {
            log.info("Error when parsing JSON schema. Make sure, that it is in valid JSON format, use a linter: http://jsonschemalint.com/draft4/#");
            e.printStackTrace();
        }

        mapper = new ObjectMapper();
    }

    public ProbeDTO validate(String message) {

        JsonNode messageNode;

        if (null == message || message.isEmpty()) {
            return null;
        }

        try {
            messageNode = JsonLoader.fromString(message) ;
        } catch (IOException e) {
            log.info("Incoming message is not even in valid JSON format!");
            return null;
        }

        ProcessingReport report;
        try {
            report = jsonSchema.validate(messageNode);
            if (report.isSuccess()) {
                try {
                    return mapper.treeToValue(messageNode, ProbeDTO.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        } catch (ProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
