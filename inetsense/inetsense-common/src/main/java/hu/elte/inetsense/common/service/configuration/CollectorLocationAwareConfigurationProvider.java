package hu.elte.inetsense.common.service.configuration;

import org.apache.commons.configuration2.ex.ConfigurationException;

import hu.elte.inetsense.common.util.PropertyUtil;

public class CollectorLocationAwareConfigurationProvider extends ConfigurationProvider {

	protected String collectorHost;
	protected int collectorPort;

	public CollectorLocationAwareConfigurationProvider(EnvironmentService environmentService) {
		super(environmentService);
	}
	
	public CollectorLocationAwareConfigurationProvider() {
		super();
	}
	
	@Override
    protected void doLoadConfiguration() throws ConfigurationException {
		fixURLConfiguration();
        super.doLoadConfiguration();
    }

	protected void fixURLConfiguration() {
		fixURLConfigurationFromProperty();
	}

	protected void fixURLConfigurationFromProperty() {
		collectorPort = PropertyUtil.getIntProperty("collector.port");
		collectorHost = PropertyUtil.getProperty("collector.host");
	}

	@Override
	public String getCollectorBaseURL() {
        return String.format("http://%s:%d", collectorHost, collectorPort);
    }
}
