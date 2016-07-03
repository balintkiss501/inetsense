package hu.elte.inetsense.probe.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationProvider {

    @Autowired
    private EnvironmentService environmentService;
    
    private static final Logger log = LogManager.getLogger();
    
    private CompositeConfiguration config = new CompositeConfiguration();

    @PostConstruct
    private void loadConfiguration() {
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
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.fileBased().setFileName(probeConfiguration));
        config.addConfiguration(builder.getConfiguration());
    }

    private void loadDefaultConfiguration() throws ConfigurationException {
        URL defaultConfigurationURL = getDefaultConfigurationURL();
        log.info("Loading default configuration from {}", defaultConfigurationURL);
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.fileBased().setURL(defaultConfigurationURL));
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
}
