package hu.elte.inetsense.common.service.configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.DatabaseConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseConfigurationProvider {

    protected CompositeConfiguration config = new CompositeConfiguration();
    private FileBasedConfigurationBuilder<FileBasedConfiguration> runtimeConfigurationBuilder;
    
    private static final Logger log = LogManager.getLogger();

    public void loadConfiguration() {
        log.info("Initializing probe configuration...");
        try {
            setupRuntimeConfig();
            doLoadConfiguration();
        } catch (ConfigurationException e) {
            log.fatal("Unable to load default configuration!", e);
        }
    }

    private void setupRuntimeConfig() throws ConfigurationException {
        runtimeConfigurationBuilder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure();
        config.addConfiguration(runtimeConfigurationBuilder.getConfiguration());
    }

    abstract protected void doLoadConfiguration() throws ConfigurationException;

    protected FileBasedConfigurationBuilder<FileBasedConfiguration> loadConfigurationFromPropertyFile(String filePath)
            throws ConfigurationException {
        log.info("Loading configuration from {}", filePath);
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> localConfigurationBuilder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.fileBased().setFileName(filePath));
        config.addConfiguration(localConfigurationBuilder.getConfiguration());
        return localConfigurationBuilder;
    }

    protected void loadConfigurationFromURL(URL defaultConfigurationURL) throws ConfigurationException {
        log.info("Loading configuration from {}", defaultConfigurationURL);
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class)
                        .configure(params.fileBased().setURL(defaultConfigurationURL)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
        config.addConfiguration(builder.getConfiguration());
    }

    // FIXME not working with injected spring data source
    protected void loadConfigurationFromDataSource(DataSource dataSource) throws ConfigurationException {
        log.info("Loading configuration from datasource.");
        BasicConfigurationBuilder<DatabaseConfiguration> builder = new BasicConfigurationBuilder<DatabaseConfiguration>(
                DatabaseConfiguration.class);
        builder.configure(new Parameters().database().setDataSource(dataSource).setTable("configuration").setKeyColumn("key")
                .setValueColumn("value"));
        config.addConfiguration(builder.getConfiguration());
    }

    public BaseConfigurationProvider() {
        super();
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
    
    public <T> void addRuntimeProperty(String key, T value) {
        try {
            runtimeConfigurationBuilder.getConfiguration().setProperty(key, value);
        } catch (ConfigurationException e) {
            log.error(e);
        }
    }
    
    public List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        config.getKeys().forEachRemaining(s -> keys.add(s));
        return keys;
    }

    public String getString(String key) {
        return config.getString(key);
    }
}