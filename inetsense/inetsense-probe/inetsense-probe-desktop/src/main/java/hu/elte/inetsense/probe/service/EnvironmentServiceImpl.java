package hu.elte.inetsense.probe.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.service.configuration.EnvironmentService;

@Component
public class EnvironmentServiceImpl implements EnvironmentService {

    private static final String BASE_DIRECTORY = ".inetsense";
    private static final String LOG_DIRECTORY = "log";
    private static final String LOG_FILE_NAME = "inetsense-probe.log";
    private static final String CONFIGURATION_DIRECTORY = "configuration";
    private static final String CONFIGURATION_FILE_NAME = "probe-configuration.properties";
    
    private final String userHome;
    
    public EnvironmentServiceImpl() {
        userHome = System.getProperty("user.home");
        reInitializeLogger();
    }

    private void reInitializeLogger() {
        System.setProperty("log-location", getLogFileBaseLocation());
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        ctx.reconfigure();
    }

    private String getLogFileBaseLocation() {
        return String.format("%s/%s/%s", userHome, BASE_DIRECTORY, LOG_DIRECTORY);
    }
    
    @Override
    public String getLogFileLocation() {
        return String.format("%s/%s", getLogFileBaseLocation(), LOG_FILE_NAME);
    }
    
    @PostConstruct
    private void createEnvironment() throws IOException {
        createConfigurationDirectory();
    }

    private void createConfigurationDirectory() throws IOException {
        File configFile = new File(getConfigurationFilePath());
        FileUtils.forceMkdirParent(configFile);
        if(!configFile.exists()) {
            InputStream defaultConfig = getClass().getResourceAsStream("/" + CONFIGURATION_FILE_NAME);
            FileUtils.copyInputStreamToFile(defaultConfig, configFile);
        }
    }

    /* (non-Javadoc)
     * @see hu.elte.inetsense.probe.service.EnvironmentService#getConfigurationFilePath()
     */
    @Override
    public String getConfigurationFilePath() {
        return String.format("%s/%s/%s/%s", userHome, BASE_DIRECTORY, CONFIGURATION_DIRECTORY, CONFIGURATION_FILE_NAME);
    }
}
