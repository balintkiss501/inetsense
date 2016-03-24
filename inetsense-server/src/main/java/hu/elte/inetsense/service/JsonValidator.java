package hu.elte.inetsense.service;

import com.google.gson.Gson;
import hu.elte.inetsense.web.dtos.JsonMessageObject;
import org.springframework.stereotype.Component;

/**
 * Service for the Standardized Interface: validation of incoming JSON data against existing JSON schema
 *
 * Created by balintkiss on 3/22/16.
 */
@Component
public class JsonValidator {

    public JsonMessageObject validate(String message) {
        Gson gson = new Gson();
        return gson.fromJson(message, JsonMessageObject.class);
    }
}
