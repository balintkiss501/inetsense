package hu.elte.inetsense.server.collector.service;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.data.entities.Measurement;

/**
 * @author Zsolt Istvanfi
 */
public interface ProbeDataService {

    void saveProbeData(ProbeDataDTO probeData);
    void processMeasurement(Measurement measurement);

}
