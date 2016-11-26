package hu.elte.inetsense.probe.service;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;

import org.apache.commons.configuration2.ex.ConfigurationException;

import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.common.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.EnvironmentService;
import hu.elte.inetsense.common.util.PropertyUtil;

public class ProbeConfigurationProvider extends ConfigurationProvider {

	private String collectorHost;
	private int collectorPort;
	
	public ProbeConfigurationProvider(EnvironmentService environmentService) {
		super(environmentService);
	}
	
	@Override
    protected void doLoadConfiguration() throws ConfigurationException {
		fixURLConfiguration();
        loadDefaultConfiguration();
        loadEnvironmentConfigurationFromFile();
        storeFixedUrlConfiguration();
    }
	
	private void fixURLConfiguration() {
        try {
            BasicService bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
            collectorHost = bs.getCodeBase().getHost();
            collectorPort = bs.getCodeBase().getPort();
        } catch (Throwable ue) {
        	collectorPort = PropertyUtil.getIntProperty("collector.port");
        	collectorHost = PropertyUtil.getProperty("collector.host");
        }
    }

	private void storeFixedUrlConfiguration() {
		changeLocalProperty(ConfigurationNames.COLLECTOR_SERVER_HOST, collectorHost);
		changeLocalProperty(ConfigurationNames.COLLECTOR_SERVER_PORT, collectorPort);
	}
	
	public String getCollectorBaseURL() {
        return String.format("http://%s:%d", collectorHost, collectorPort);
    }

}
