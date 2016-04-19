package hu.elte.inetsense.web;

import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.service.ProbeDataService;
import hu.elte.inetsense.web.dtos.ProbeDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * JSON message endpoint for the Standardized Interface.
 */
@RestController
@RequestMapping("/message-endpoint")
public class JsonValidatorController {

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
        ProbeDataDTO jsonMessageObject = validator.validate(message);

        if (null == jsonMessageObject) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("HTTP 500 error: Incoming JSON message validation has failed.");  // TODO: adding Spring's default JSON-based response
        }

        probeDataService.saveProbeData(jsonMessageObject);

        return ResponseEntity.ok("HTTP 200: Incoming JSON message validation was successful! Keep up the good work!"); // TODO: adding Spring's default JSON-based response
    }
}
