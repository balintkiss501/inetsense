package hu.elte.inetsense.service;

import java.util.List;

import hu.elte.inetsense.web.dtos.MeasurementDTO;

/**
 * @author Zsolt Istvanfi
 */
public interface MeasurementService {

    List<MeasurementDTO> getAllMeasurements();

}
