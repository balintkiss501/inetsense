package hu.elte.inetsense.probe;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import hu.elte.inetsense.probe.service.DownloadSpeedMeterService;
import hu.elte.inetsense.probe.service.MeasurementService;
import hu.elte.inetsense.probe.service.configuration.ClockService;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.probe.service.configuration.EnvironmentService;

@Configuration
@ComponentScan(value = "hu.elte.inetsense.probe")
@EnableScheduling
public class ProbeConfiguration implements SchedulingConfigurer {

    // HACK: EnvironmentService should be created first
    @SuppressWarnings("unused")
    @Autowired
    private EnvironmentService environmentService;
    
    @Bean(initMethod = "loadConfiguration")
    public ConfigurationProvider configurationProvider(EnvironmentService environmentService) {
        ConfigurationProvider configurationProvider = new ConfigurationProvider(environmentService);
        return configurationProvider;
    }
    
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod="shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }
    
    @Bean
    public DownloadSpeedMeterService downloadSpeedMeterService(ConfigurationProvider configurationProvider) {
        return new DownloadSpeedMeterService(configurationProvider);
    }
    
    @Bean
    public ClockService clockService(ConfigurationProvider configurationProvider) {
        return new ClockService(configurationProvider);
    }
    
    @Bean
    public MeasurementService measurementService(ConfigurationProvider configurationProvider, DownloadSpeedMeterService downloadSpeedMeterService, ClockService clockService) {
        return new MeasurementService(configurationProvider, downloadSpeedMeterService, clockService);
    }
}
