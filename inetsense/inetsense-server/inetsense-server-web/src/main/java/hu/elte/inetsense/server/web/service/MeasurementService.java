package hu.elte.inetsense.server.web.service;

import java.util.List;

import hu.elte.inetsense.common.dtos.MeasurementDTO;

/**
 * @author Zsolt Istvanfi
 */
public interface MeasurementService {

    List<MeasurementDTO> getAllMeasurements();

}
