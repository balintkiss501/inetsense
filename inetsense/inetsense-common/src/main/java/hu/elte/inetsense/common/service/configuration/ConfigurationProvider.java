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

    protected void loadDefaultConfiguration() throws ConfigurationException {
        URL defaultConfigurationURL = getDefaultConfigurationURL();
        loadConfigurationFromURL(defaultConfigurationURL);
    }

    protected URL getDefaultConfigurationURL() {
        try {
            return new URL(getCollectorBaseURL() + "/configuration.properties");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void changeLocalProperty(ConfigurationNames property, T value) {
        try {
            localConfigurationBuilder.getConfiguration().setProperty(property.getKey(), value);
            localConfigurationBuilder.save();
        } catch (ConfigurationException e) {
            log.error("Failed to change property: {} to value: {}", property.getKey(), value, e);
            throw new RuntimeException(e);
        }
    }

    public String getCollectorBaseURL() {
        String host = getString(ConfigurationNames.COLLECTOR_SERVER_HOST);
        int port = getInt(ConfigurationNames.COLLECTOR_SERVER_PORT);
        return String.format("http://%s:%d", host, port);
    }
}
