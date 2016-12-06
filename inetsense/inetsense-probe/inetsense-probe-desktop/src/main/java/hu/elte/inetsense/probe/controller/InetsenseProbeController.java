package hu.elte.inetsense.probe.controller;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import hu.elte.inetsense.common.dtos.probe.ProbeConfigurationDTO;
import hu.elte.inetsense.common.service.configuration.ClockService;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.common.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.common.util.HTTPUtil;
import hu.elte.inetsense.probe.service.IspService;
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
    private IspService ispService;
    
    @Autowired
    private ClockService clockService;
    
    private boolean guiEnabled;

    public void start() {
        printProbeInformation();
        if (initProbeConfiguration()) {
            ScheduledExecutorService ses = (ScheduledExecutorService) taskExecutor;
            int delay = configurationProvider.getInt(ConfigurationNames.TEST_INTERVAL);
            int timeDelay = 10 * 60 * 1000;
            int ispDelay = 30 * 60 * 1000;
            log.info("Scheduling measurement services...");
            ses.scheduleWithFixedDelay(() -> measurementService.measure(), 500, delay, TimeUnit.MILLISECONDS);
            log.info("Scheduling clock service...");
            ses.scheduleWithFixedDelay(() -> clockService.refreshClock(), 100, timeDelay, TimeUnit.MILLISECONDS);
            log.info("Scheduling isp service...");
            ses.scheduleWithFixedDelay(() -> ispService.updateIsp(), 100, ispDelay, TimeUnit.MILLISECONDS);
        }
    }

    private boolean initProbeConfiguration() {
        String probeId = configurationProvider.getString(ConfigurationNames.PROBE_ID);
        while (isDefaultProbeId(probeId) || !isExistingProbe(probeId)) {
            log.warn("Pobe ID is not valid: {}", probeId);

            if (!guiEnabled) {
                return false;
            }

            probeId = JOptionPane.showInputDialog("Enter probe id");
            if (probeId == null) {
                int confirmResult = JOptionPane.showConfirmDialog(null, "Would you like to close the application?", null, JOptionPane.YES_NO_OPTION);
                if (confirmResult == JOptionPane.YES_OPTION) {
                	System.exit(0);
                }
            } else {
            	log.info("Setting new Probe Id: {}", probeId);
                configurationProvider.changeLocalProperty(ConfigurationNames.PROBE_ID, probeId);
            }
        }
        loadProbeConfiguration(probeId);configurationProvider.getInt(ConfigurationNames.TEST_INTERVAL);
        return true;
    }

    private void loadProbeConfiguration(String probeId) {
    	String baseUrl = configurationProvider.getCollectorBaseURL();
        CloseableHttpResponse response = HTTPUtil.get(baseUrl + "/probe/" + probeId);
        try {
			String responseAsString = EntityUtils.toString(response.getEntity());
			ObjectMapper mapper = new ObjectMapper();
			List<ProbeConfigurationDTO> probeConfigurationList = mapper.readValue(responseAsString, new TypeReference<List<ProbeConfigurationDTO>>(){});
			storeProbeConfig(probeConfigurationList);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void storeProbeConfig(List<ProbeConfigurationDTO> probeConfigurationList) {
		probeConfigurationList.forEach(
			config -> configurationProvider.changeLocalProperty(config.getKey(), config.getValue())
		);
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

    public void setGuiEnabled(boolean enabled) {
        guiEnabled = enabled;
    }
}
