package hu.elte.inetsense.server.collector.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.service.configuration.BaseConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.server.collector.util.VersionInfo;
import hu.elte.inetsense.server.data.ConfigurationRepository;
import hu.elte.inetsense.server.data.entities.Configuration;

@Component
public class CollectorConfigurationProvider extends BaseConfigurationProvider {

    @Autowired
    private ConfigurationRepository configurationRepository;
    
    @Autowired
    private VersionInfo versionInfo;
    
    @Override
    @PostConstruct
    public void loadConfiguration() {
        super.loadConfiguration();
    }
    
    @Override
    protected void doLoadConfiguration() throws ConfigurationException {
        List<Configuration> configs = configurationRepository.findAll();
        configs.forEach(c -> addRuntimeProperty(c.getKey(), c.getValue()));
        addRuntimeProperty(ConfigurationNames.INETSENSE_PROJECT_VERSION.getKey(), versionInfo.getVersion());
        addRuntimeProperty(ConfigurationNames.INETSENSE_PROJECT_BUILD_DATE.getKey(), versionInfo.getBuilddate());
    }
}
