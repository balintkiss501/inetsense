package hu.elte.inetsense.upload.service;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.service.configuration.CollectorLocationAwareConfigurationProvider;

@Component
public class UploadServerConfigurationProvider extends CollectorLocationAwareConfigurationProvider {

    @Override
    @PostConstruct
    public void loadConfiguration() {
        super.loadConfiguration();
    }
    
    @Override
    protected void doLoadConfiguration() throws ConfigurationException {
        loadDefaultConfiguration();
    }


}
