package hu.elte.inetsense.server.collector.service.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.ProbeDataService;

@Component
public class ProbeDataReceiver {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    public static final String DESTINATION = "INETSENSE_MEASUREMENT_IN";
    
    @Autowired
    private ProbeDataService probeDataService;
    
	@JmsListener(destination = DESTINATION, concurrency = "1-10", containerFactory = "inetsenseJmsFactory")
	public void onMessage(ProbeDataDTO probeData) {
		log.info("Recieved meausrement for probe: " + probeData.getProbeAuthId());
		probeDataService.processProbeData(probeData);
	}
}
