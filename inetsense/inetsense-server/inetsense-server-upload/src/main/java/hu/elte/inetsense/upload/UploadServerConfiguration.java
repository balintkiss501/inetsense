package hu.elte.inetsense.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import hu.elte.inetsense.probe.service.configuration.ClockService;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.probe.service.configuration.EnvironmentService;

@Configuration
@ComponentScan(value = "hu.elte.inetsense.upload")
@EnableScheduling
public class UploadServerConfiguration {

    // HACK: EnvironmentService should be created first
    @SuppressWarnings("unused")
    @Autowired
    private EnvironmentService environmentService;
    
    @Bean(initMethod = "loadConfiguration")
    public ConfigurationProvider configurationProvider(EnvironmentService environmentService) {
        ConfigurationProvider configurationProvider = new ConfigurationProvider(environmentService);
        return configurationProvider;
    }

    @Bean
    public ClockService clockService(ConfigurationProvider configurationProvider) {
        return new ClockService(configurationProvider);
    }
    
}
