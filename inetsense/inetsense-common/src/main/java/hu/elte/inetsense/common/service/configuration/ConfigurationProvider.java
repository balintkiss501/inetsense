package hu.elte.inetsense.common.service.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationProvider extends BaseConfigurationProvider {

    private EnvironmentService environmentService;

    private static final Logger log = LogManager.getLogger();

    private FileBasedConfigurationBuilder<FileBasedConfiguration> localConfigurationBuilder;

    public ConfigurationProvider(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }
    
    public ConfigurationProvider() {
    }

    @Override
    protected void doLoadConfiguration() throws ConfigurationException {
        loadDefaultConfiguration();
        loadEnvironmentConfigurationFromFile();
    }

    private void loadEnvironmentConfigurationFromFile() throws ConfigurationException {
        String probeConfiguration = environmentService.getConfigurationFilePath();
        localConfigurationBuilder = loadConfigurationFromPropertyFile(probeConfiguration);
    }

    private void loadDefaultConfiguration() throws ConfigurationException {
        URL defaultConfigurationURL = getDefaultConfigurationURL();
        loadConfigurationFromURL(defaultConfigurationURL);
    }

    private URL getDefaultConfigurationURL() {
        try {
            return new URL("http://localhost:8080/configuration.properties");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void changeLocalProperty(String property, T value) {
        try {
            localConfigurationBuilder.getConfiguration().setProperty(property, value);
            localConfigurationBuilder.save();
        } catch (ConfigurationException e) {
            log.error("Failed to change property: {} to value: {}", property, value, e);
            throw new RuntimeException(e);
        }
    }

    public String getCollectorBaseURL() {
        return "http://localhost:8080";
    }
}
