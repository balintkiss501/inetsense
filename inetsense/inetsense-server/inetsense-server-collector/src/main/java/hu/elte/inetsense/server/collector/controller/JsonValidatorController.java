package hu.elte.inetsense.server.collector.controller;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.JsonValidator;
import hu.elte.inetsense.server.collector.service.ProbeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by balintkiss on 3/25/16.
 */

/**
 * TODO: storing object not implemented yet.
 */
@RestController
@RequestMapping("/message-endpoint")
public class JsonValidatorController {

    @Autowired
    private JsonValidator validator;

    @Autowired
    private ProbeDataService probeDataService;

    /**
     * TODO: This doesn't do anything yet besides schema-based validation, but trust me,
     * it validates if you send JSON.
     *
     * * curl -X POST -H "Content-Type: application/json" -d @valid-testdata.json http://localhost:8080/message-endpoint
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> validateMessage(@RequestBody String message) {
        ProbeDataDTO jsonMessageObject = validator.validate(message);

        if (null == jsonMessageObject) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("HTTP 500 error: Incoming JSON message validation has failed.");  // TODO: adding Spring's default JSON-based response
        }

        probeDataService.saveProbeData(jsonMessageObject);

        return ResponseEntity.ok("HTTP 200: Incoming JSON message validation was successful! Keep up the good work! To be continued..."); // TODO: adding Spring's default JSON-based response
    }
}
