package hu.elte.inetsense.server.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import hu.elte.inetsense.server.service.configuration.ServerConfigurationProvider;
import hu.elte.inetsense.server.service.configuration.VersionInfo;

@Configuration
@EnableScheduling
public class AppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/").addResourceLocations("/index.html");
    }

    @Bean
    public ObjectMapper objectMapper() {
        // Display all the digits of BigDecimals in JSON, without any shortening
        ObjectMapper om = new ObjectMapper();
        om.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
        return om;
    }

    @Bean
    public ServerConfigurationProvider serverConfigurationProvider() {
        return new ServerConfigurationProvider();
    }

    @Bean
    public VersionInfo versionInfo() {
        return new VersionInfo();
    }

}
