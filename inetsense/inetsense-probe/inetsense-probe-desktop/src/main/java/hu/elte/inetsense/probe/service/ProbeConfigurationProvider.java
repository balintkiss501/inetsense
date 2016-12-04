package hu.elte.inetsense.probe.service;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.common.service.configuration.CollectorLocationAwareConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.common.service.configuration.EnvironmentService;

public class ProbeConfigurationProvider extends CollectorLocationAwareConfigurationProvider {

    private static final Logger log = LogManager.getLogger();
	
	public ProbeConfigurationProvider(EnvironmentService environmentService) {
		super(environmentService);
	}
	
	@Override
    protected void doLoadConfiguration() throws ConfigurationException {
		log.info("Loading configuration for probe");
        super.doLoadConfiguration();
        storeFixedUrlConfiguration();
    }
	
	@Override
	protected void fixURLConfiguration() {
        try {
        	log.info("Loading collector location from JNLP...");
            BasicService bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
            collectorHost = bs.getCodeBase().getHost();
            collectorPort = bs.getCodeBase().getPort();
        } catch (Throwable ue) {
        	log.info("Application was not launched with JNLP, falling back to property configuration.");
        	fixURLConfigurationFromProperty();
        }
    }

	private void storeFixedUrlConfiguration() {
		log.info("Store configuration for collector location");
		changeLocalProperty(ConfigurationNames.COLLECTOR_SERVER_HOST, collectorHost);
		changeLocalProperty(ConfigurationNames.COLLECTOR_SERVER_PORT, collectorPort);
	}

}
