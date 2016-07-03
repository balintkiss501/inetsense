package hu.elte.inetsense.probe.controller;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.probe.service.MeasurementService;
import hu.elte.inetsense.probe.service.configuration.ConfigurationNames;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;

@Component
public class InetsenseProbeController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Autowired
    private Executor taskExecutor;

    @Autowired
    private MeasurementService measurementService;

    public void start() {
        printProbeInformation();
        initProbeId();
        ScheduledExecutorService ses = (ScheduledExecutorService) taskExecutor;
        int delay = configurationProvider.getInt(ConfigurationNames.TEST_INTERVAL);
        ses.scheduleAtFixedRate(() -> doMeasurement(), 100, delay, TimeUnit.MILLISECONDS);
    }

    private void doMeasurement() {
        measurementService.measure();
    }

    private void initProbeId() {
        if (isDefaultProbeId()) {
            String newProbeId = JOptionPane.showInputDialog("Enter probe id");
            configurationProvider.changeLocalProperty(ConfigurationNames.PROBE_ID.getKey(), newProbeId);
        }
    }

    private boolean isDefaultProbeId() {
        String probeId = configurationProvider.getString(ConfigurationNames.PROBE_ID);
        return Objects.equals(probeId, ConfigurationNames.PROBE_ID.getDefalultValue());
    }

    private void printProbeInformation() {
        String javaVersion = System.getProperty("java.version");
        String os = System.getProperty("os.name");
        String inetSenseVersion = configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_VERSION);
        String buildDate = configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_BUILD_DATE);
        log.info("============================= INETSENSE =============================");
        log.info("Starting InetSense Probe application");
        log.info("Version: {}", inetSenseVersion);
        log.info("Built on: {}", buildDate);
        log.info("Java Version: {}", javaVersion);
        log.info("OS: {}", os);
        log.info("=====================================================================");
    }

}
