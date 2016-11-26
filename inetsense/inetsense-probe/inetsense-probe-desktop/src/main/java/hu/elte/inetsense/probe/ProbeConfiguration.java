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

import hu.elte.inetsense.common.service.configuration.BaseConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.ClockService;
import hu.elte.inetsense.common.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.EnvironmentService;
import hu.elte.inetsense.common.util.JsonConverter;
import hu.elte.inetsense.common.util.PropertyUtil;
import hu.elte.inetsense.probe.service.DownloadSpeedMeterService;
import hu.elte.inetsense.probe.service.MeasurementService;
import hu.elte.inetsense.probe.service.ProbeConfigurationProvider;
import hu.elte.inetsense.probe.service.UploadSpeedMeterService;
import hu.elte.inetsense.probe.view.ProbeView;

@Configuration
@ComponentScan(value = "hu.elte.inetsense.probe")
@EnableScheduling
public class ProbeConfiguration implements SchedulingConfigurer {

    // HACK: EnvironmentService should be created first
    @SuppressWarnings("unused")
    @Autowired
    private EnvironmentService environmentService;

    @Bean
    public ConfigurationProvider configurationProvider(EnvironmentService environmentService) {
        ConfigurationProvider configurationProvider = new ProbeConfigurationProvider(environmentService);
        configurationProvider.loadConfiguration();
        return configurationProvider;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

    @Bean
    public DownloadSpeedMeterService downloadSpeedMeterService(BaseConfigurationProvider configurationProvider) {
        return new DownloadSpeedMeterService(configurationProvider);
    }

    @Bean
    public UploadSpeedMeterService uploadSpeedMeterService(BaseConfigurationProvider configurationProvider) {
        return new UploadSpeedMeterService(configurationProvider);
    }

    @Bean
    public ClockService clockService(ConfigurationProvider configurationProvider) {
        return new ClockService(configurationProvider);
    }

    @Bean
    public JsonConverter jsonConverter(BaseConfigurationProvider configurationProvider) {
        return new JsonConverter();
    }

    @Bean
    public MeasurementService measurementService(ConfigurationProvider configurationProvider,
            DownloadSpeedMeterService downloadSpeedMeterService, UploadSpeedMeterService uploadSpeedMeterService,
            ClockService clockService, JsonConverter jsonConverter) {
        return new MeasurementService(configurationProvider, downloadSpeedMeterService, uploadSpeedMeterService,
                clockService, jsonConverter);
    }

    @Bean
    public ProbeView probeView(EnvironmentService environmentService) {
        boolean headless = PropertyUtil.getBooleanProperty("headless");
        return headless ? null : new ProbeView(environmentService);
    }
}
