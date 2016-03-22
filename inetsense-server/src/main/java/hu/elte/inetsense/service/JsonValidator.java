package hu.elte.inetsense.service;

import com.google.gson.Gson;
import hu.elte.inetsense.domain.entities.JsonMessageObject;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Service for the Standardized Interface: validation of incoming JSON data against existing JSON schema
 *
 * Created by balintkiss on 3/22/16.
 */
// TODO: Using as plain class in my unit tests, until figuring out what Dozer is
//@Component
public class JsonValidator {

    public JsonMessageObject validate(String message) {
        Gson gson = new Gson();
        JsonMessageObject obj = gson.fromJson(message, JsonMessageObject.class);
        return obj;
    }
}
