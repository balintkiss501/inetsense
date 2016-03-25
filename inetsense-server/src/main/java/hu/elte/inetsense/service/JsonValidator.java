package hu.elte.inetsense.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import hu.elte.inetsense.web.dtos.ProbeDTO;
import org.springframework.stereotype.Component;

/**
 * Service for the Standardized Interface: validation of incoming JSON data against existing JSON schema
 *
 * Created by balintkiss on 3/22/16.
 */
@Component
public class JsonValidator {

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

    public ProbeDTO validate(String message) {
        return gson.fromJson(message, ProbeDTO.class);
    }
}
