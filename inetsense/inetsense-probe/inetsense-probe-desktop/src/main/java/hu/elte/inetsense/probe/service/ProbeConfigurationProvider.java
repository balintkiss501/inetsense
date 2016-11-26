package hu.elte.inetsense.probe.service;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;

import org.apache.commons.configuration2.ex.ConfigurationException;

import hu.elte.inetsense.common.service.configuration.CollectorLocationAwareConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.common.service.configuration.EnvironmentService;

public class ProbeConfigurationProvider extends CollectorLocationAwareConfigurationProvider {
	
	public ProbeConfigurationProvider(EnvironmentService environmentService) {
		super(environmentService);
	}
	
	@Override
    protected void doLoadConfiguration() throws ConfigurationException {
        super.doLoadConfiguration();
        storeFixedUrlConfiguration();
    }
	
	@Override
	protected void fixURLConfiguration() {
        try {
            BasicService bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
            collectorHost = bs.getCodeBase().getHost();
            collectorPort = bs.getCodeBase().getPort();
        } catch (Throwable ue) {
        	fixURLConfigurationFromProperty();
        }
    }

	private void storeFixedUrlConfiguration() {
		changeLocalProperty(ConfigurationNames.COLLECTOR_SERVER_HOST, collectorHost);
		changeLocalProperty(ConfigurationNames.COLLECTOR_SERVER_PORT, collectorPort);
	}

}
