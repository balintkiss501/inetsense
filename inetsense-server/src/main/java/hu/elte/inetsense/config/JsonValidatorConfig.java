package hu.elte.inetsense.config;

import hu.elte.inetsense.service.JsonValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean configuration which can be used alone while testing and
 * can be used in conjunction with other configs.
 *
 * Created by balintkiss on 3/30/16.
 */
@Configuration
public class JsonValidatorConfig {

    @Bean
    public JsonValidator getJsonValidatorBean() {
        return new JsonValidator();
    }
}
