package hu.elte.inetsense.probe.service.configuration;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationProvider {

    private EnvironmentService environmentService;
    
    private static final Logger log = LogManager.getLogger();
    
    private CompositeConfiguration config = new CompositeConfiguration();

    private FileBasedConfigurationBuilder<FileBasedConfiguration> localConfigurationBuilder;
    
    public ConfigurationProvider(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    public void loadConfiguration() {
        log.info("Initializing probe configuration...");
        try {
            loadDefaultConfiguration();
            loadProbeConfiguration();
        } catch (ConfigurationException e) {
            log.fatal("Unable to load default configuration!", e);
        }
    }

    private void loadProbeConfiguration() throws ConfigurationException {
        String probeConfiguration = environmentService.getConfigurationFilePath();
        log.info("Loading default configuration from {}", probeConfiguration );
        Parameters params = new Parameters();
        localConfigurationBuilder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.fileBased().setFileName(probeConfiguration));
        config.addConfiguration(localConfigurationBuilder.getConfiguration());
    }

    private void loadDefaultConfiguration() throws ConfigurationException {
        URL defaultConfigurationURL = getDefaultConfigurationURL();
        log.info("Loading default configuration from {}", defaultConfigurationURL);
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.fileBased().setURL(defaultConfigurationURL).setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config.addConfiguration(builder.getConfiguration());
    }

    private URL getDefaultConfigurationURL() {
        try {
            return new URL("http://localhost:8080/configuration.properties");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String getString(ConfigurationNames conf) {
        return config.getString(conf.getKey(), (String) conf.getDefalultValue());
    }
    

    public int getInt(ConfigurationNames conf) {
        return config.getInt(conf.getKey(), (Integer) conf.getDefalultValue());
    }

    public long getLong(ConfigurationNames conf) {
        return config.getLong(conf.getKey(), (Long) conf.getDefalultValue());
    }
    
    public String[] getStringArray(ConfigurationNames conf) {
        return config.getStringArray(conf.getKey());
    }
    
    public <T> void changeLocalProperty(String property, T value)  {
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
