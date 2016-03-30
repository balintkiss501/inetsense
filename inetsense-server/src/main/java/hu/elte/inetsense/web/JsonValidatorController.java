package hu.elte.inetsense.web;

import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.web.dtos.ProbeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     *
     * @param message
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> validateMessage(@PathVariable String message) {
        ProbeDTO jsonMessageObject = validator.validate(message);

        if (null == jsonMessageObject) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Incoming JSON message validation has failed.");
        }

        return ResponseEntity.ok("Incoming JSON message validation was successful!");
    }
}
