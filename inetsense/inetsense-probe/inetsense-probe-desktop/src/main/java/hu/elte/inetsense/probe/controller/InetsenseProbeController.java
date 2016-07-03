package hu.elte.inetsense.probe.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.probe.service.ConfigurationNames;
import hu.elte.inetsense.probe.service.ConfigurationProvider;

@Component
public class InetsenseProbeController {

    private static final Logger log = LogManager.getLogger();
    
    @Autowired
    private ConfigurationProvider configurationProvider;
    
    public void start() {
        printProbeInformation();
    }

    
    private void printProbeInformation() {
        String javaVersion = System.getProperty("java.version");
        String os = System.getProperty("os.name");
        String inetSenseVersion = configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_VERSION);
        String buildDate  = configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_BUILD_DATE);
        log.info("============================= INETSENSE =============================");
        log.info("Starting InetSense Probe application");
        log.info("Version: {}", inetSenseVersion);
        log.info("Built on: {}", buildDate);
        log.info("Java Version: {}", javaVersion);
        log.info("OS: {}", os);
        log.info("=====================================================================");
    }

}
