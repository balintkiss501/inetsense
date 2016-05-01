package hu.elte.inetsense.server.collector.controller;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.ProbeDataService;
import hu.elte.inetsense.server.collector.service.JsonValidator;

import hu.elte.inetsense.server.collector.util.JsonValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * JSON message endpoint for the Standardized Interface.
 */
@RestController
@RequestMapping("/message-endpoint")
public class JsonValidatorController {

    private final Logger log = LoggerFactory.getLogger(JsonValidatorController.class);

    @Autowired
    private JsonValidator validator;

    @Autowired
    private ProbeDataService probeDataService;

    /**
     * Validate incoming JSON message and save Probe data.
     * <p>
     * To test: curl -X POST -H "Content-Type: application/json" -d @valid-testdata.json http://localhost:8080/message-endpoint
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> validateMessage(@RequestBody String message) {

        ProbeDataDTO jsonMessageObject;

        // Validation
        try {
            jsonMessageObject = validator.validate(message);
            log.info("Incoming JSON message validation was successful.");
        } catch (JsonValidationException e) {
            log.error("Incoming JSON message validation has failed due to this reason:\n" +
            e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("HTTP 500 error: Incoming JSON message validation has failed due to this reason:\n" +
                            e.getMessage());  // TODO: adding Spring's default JSON-based response
        }

        // Saving
        if (jsonMessageObject != null) {
            log.info("Saving data...");
            probeDataService.saveProbeData(jsonMessageObject);
            log.info("Saving data was successful.");

            return ResponseEntity.ok("HTTP 200: Incoming JSON message validation and data saving was successfull."); // TODO: adding Spring's default JSON-based response
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("HTTP 500 error: Internal error happened when saving data after successful validation.");  // TODO: adding Spring's default JSON-based response
    }
}
