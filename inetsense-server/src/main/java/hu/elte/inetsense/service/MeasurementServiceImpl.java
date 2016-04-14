package hu.elte.inetsense.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.domain.MeasurementRepository;
import hu.elte.inetsense.domain.entities.Measurement;
import hu.elte.inetsense.web.dtos.MeasurementDTO;

/**
 * @author Zsolt Istvanfi
 */
@Component
@Transactional(readOnly = true)
public class MeasurementServiceImpl extends AbstractService implements MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public List<MeasurementDTO> getAllMeasurements() {
        List<Measurement> measurements = measurementRepository.findAll();

        List<MeasurementDTO> measurementDTOs = new ArrayList<>(measurements.size());
        for (Measurement measurement : measurements) {
            MeasurementDTO dto = new MeasurementDTO();
            dto.setCompletedOn(measurement.getCompletedOn());
            dto.setDownloadSpeed(measurement.getDownloadSpeed());
            dto.setUploadSpeed(measurement.getUploadSpeed());
            dto.setLat(measurement.getLatitude());
            dto.setLng(measurement.getLongitude());
            measurementDTOs.add(dto);
        }

        return measurementDTOs;
    }

}
