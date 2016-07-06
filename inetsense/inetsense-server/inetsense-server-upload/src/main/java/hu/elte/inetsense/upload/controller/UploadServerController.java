package hu.elte.inetsense.upload.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.probe.service.configuration.ConfigurationNames;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.upload.service.UploadServer;

@Component
public class UploadServerController {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Autowired
    private UploadServer uploadServer;
    
    public void start() {
        int port = configurationProvider.getInt(ConfigurationNames.UPLOAD_SERVER_PORT);
        printServerInformation(port);
        try {
            uploadServer.start(port);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private void printServerInformation(int port) {
        String javaVersion = System.getProperty("java.version");
        String os = System.getProperty("os.name");
        String inetSenseVersion = configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_VERSION);
        String buildDate = configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_BUILD_DATE);
        log.info("====================== INETSENSE UPLOAD SERVER ======================");
        log.info("Starting InetSense Probe application");
        log.info("Version: {}", inetSenseVersion);
        log.info("Built on: {}", buildDate);
        log.info("Java Version: {}", javaVersion);
        log.info("OS: {}", os);
        log.info("Upload server port: {}", port);
        log.info("=====================================================================");
    }

}
