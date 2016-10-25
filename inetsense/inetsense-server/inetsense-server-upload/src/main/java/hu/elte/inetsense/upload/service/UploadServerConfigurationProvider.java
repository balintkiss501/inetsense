package hu.elte.inetsense.upload.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.service.configuration.ConfigurationProvider;

@Component
public class UploadServerConfigurationProvider extends ConfigurationProvider {

    @Override
    @PostConstruct
    public void loadConfiguration() {
    	initLocalConfiguration();
        super.loadConfiguration();
    }
    
    @Override
    protected void doLoadConfiguration() throws ConfigurationException {
    	loadDefaultConfiguration();
    }

    @Override
    protected URL getDefaultConfigurationURL() {
        try {
            return new URL("http://localhost:8080/configuration.properties");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
