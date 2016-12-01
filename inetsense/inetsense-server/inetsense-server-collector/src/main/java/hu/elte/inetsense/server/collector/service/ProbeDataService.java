package hu.elte.inetsense.server.collector.service;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;

/**
 * @author Zsolt Istvanfi
 */
public interface ProbeDataService {

    void saveProbeData(ProbeDataDTO probeData);
    void processProbeData(ProbeDataDTO probeData);

}
