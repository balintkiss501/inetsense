package hu.elte.inetsense.server.collector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import hu.elte.inetsense.common.util.JsonConverter;

@Configuration
@EnableScheduling
public class AppConfig {
    @Bean
    public JsonConverter jsonConverter() {
        return new JsonConverter();
    }
}