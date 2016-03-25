package hu.elte.inetsense.web;

import hu.elte.inetsense.service.JsonValidator;
import hu.elte.inetsense.web.dtos.ProbeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by balintkiss on 3/25/16.
 */

/**
 * TODO
 */
@RestController
@RequestMapping("/message-endpoint/{message}")
public class JsonValidatorController {

    @Autowired
    private JsonValidator validator;

    @RequestMapping(method = RequestMethod.POST)
    public String validateMessage(@PathVariable String message) {
        ProbeDTO jsonMessageObject = validator.validate(message);

        return "Yay cool";
    }
}
