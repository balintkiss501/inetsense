package hu.elte.inetsense.server.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.server.data.MeasurementRepository;
import hu.elte.inetsense.server.data.entities.Measurement;

/**
 * @author Zsolt Istvanfi
 */
@Component
@Transactional(readOnly = true)
public class MeasurementServiceImpl implements MeasurementService {

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
