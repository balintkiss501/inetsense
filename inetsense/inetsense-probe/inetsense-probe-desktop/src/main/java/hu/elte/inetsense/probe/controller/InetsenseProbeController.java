package hu.elte.inetsense.probe.controller;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.service.configuration.ClockService;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.common.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.common.util.HTTPUtil;
import hu.elte.inetsense.probe.service.MeasurementService;

@Component
public class InetsenseProbeController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Autowired
    private Executor taskExecutor;

    @Autowired
    private MeasurementService measurementService;
    
    @Autowired
    private ClockService clockService;

    public void start() {
        printProbeInformation();
        initProbeId();
        ScheduledExecutorService ses = (ScheduledExecutorService) taskExecutor;
        int delay = configurationProvider.getInt(ConfigurationNames.TEST_INTERVAL);
        int timeDelay = 10 * 60 * 1000;
        ses.scheduleWithFixedDelay(() -> measurementService.measure(), 500, delay, TimeUnit.MILLISECONDS);
        ses.scheduleWithFixedDelay(() -> clockService.refreshClock(), 100, timeDelay, TimeUnit.MILLISECONDS);
    }


    private void initProbeId() {
        String probeId = configurationProvider.getString(ConfigurationNames.PROBE_ID);
        while (isDefaultProbeId(probeId) || !isExistingProbe(probeId)) {
            log.info("Pobe ID is not valid: {}", probeId);
            probeId = JOptionPane.showInputDialog("Enter probe id");
            configurationProvider.changeLocalProperty(ConfigurationNames.PROBE_ID, probeId);
        }
    }

    private boolean isExistingProbe(String probeId) {
        String baseUrl = configurationProvider.getCollectorBaseURL();
        CloseableHttpResponse response = HTTPUtil.get(baseUrl + "/probe/" + probeId);
        return 200 == response.getStatusLine().getStatusCode();
    }

    private boolean isDefaultProbeId(String probeId) {
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
