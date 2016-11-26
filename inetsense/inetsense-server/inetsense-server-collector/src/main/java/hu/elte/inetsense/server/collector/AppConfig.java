package hu.elte.inetsense.server.collector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import hu.elte.inetsense.server.service.configuration.ServerConfigurationProvider;
import hu.elte.inetsense.server.service.configuration.VersionInfo;

@Configuration
@EnableScheduling
public class AppConfig extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
            "classpath:/resources/", "classpath:/static/", "classpath:/public/" };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).favorParameter(true).mediaType("xml", MediaType.valueOf("application/x-java-jnlp-file"));
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