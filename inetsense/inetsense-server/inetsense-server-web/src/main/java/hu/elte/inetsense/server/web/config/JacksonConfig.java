package hu.elte.inetsense.server.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 * @author Zsolt Istvanfi
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        // Display all the digits of BigDecimals in JSON, without any shortening
        ObjectMapper om = new ObjectMapper();
        om.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
        return om;
    }

}
