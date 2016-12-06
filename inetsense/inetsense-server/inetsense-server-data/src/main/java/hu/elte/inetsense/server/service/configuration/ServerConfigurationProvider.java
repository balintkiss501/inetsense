package hu.elte.inetsense.server.service.configuration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.service.configuration.BaseConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.server.data.entities.Configuration;
import hu.elte.inetsense.server.data.repository.ConfigurationRepository;

@Component
public class ServerConfigurationProvider extends BaseConfigurationProvider {

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
        loadConfigurationFromDatabase();
        addRuntimeProperty(ConfigurationNames.INETSENSE_PROJECT_VERSION.getKey(), versionInfo.getVersion());
        addRuntimeProperty(ConfigurationNames.INETSENSE_PROJECT_BUILD_DATE.getKey(), versionInfo.getBuilddate());
    }


    @Scheduled(initialDelay = 10 * 60 * 1000, fixedRate = 10 * 60 * 1000)
	private void loadConfigurationFromDatabase() {
		List<Configuration> configs = configurationRepository.findAll();
        configs.forEach(c -> addRuntimeProperty(c.getKey(), c.getValue()));
	}
}
