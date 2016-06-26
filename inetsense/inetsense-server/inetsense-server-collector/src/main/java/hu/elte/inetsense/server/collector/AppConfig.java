package hu.elte.inetsense.server.collector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.elte.inetsense.common.util.JsonConverter;

@Configuration
public class AppConfig {
    @Bean
    public JsonConverter jsonConverter() {
        return new JsonConverter();
    }
}