package hu.elte.inetsense.server.web.service;

import java.util.Date;
import java.util.List;

import hu.elte.inetsense.common.dtos.MeasurementDTO;

/**
 * @author Zsolt Istvanfi
 */
public interface MeasurementService {

    List<MeasurementDTO> getAllMeasurements();

    List<MeasurementDTO> getMeasurementsByProbeAuthIdBetweenDates(String probeAuthId, Date from, Date to);

}
