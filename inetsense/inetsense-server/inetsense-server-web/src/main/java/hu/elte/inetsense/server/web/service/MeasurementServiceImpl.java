package hu.elte.inetsense.server.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
        return entitiesToDTOs(measurementRepository.findAll());
    }

    @Override
    public List<MeasurementDTO> getMeasurementsByProbeAuthIdBetweenDates(final String probeAuthId,
            final Date from, final Date to) {
        return entitiesToDTOs(
                measurementRepository.findAllByProbeAuthIdAndCompletedOnBetweenOrderByCompletedOnAsc(probeAuthId, from, to));
    }

    @Override
    public List<MeasurementDTO> getAllMeasurementsByProbeAuthId(final String probeAuthId) {
        return entitiesToDTOs(measurementRepository.findAllByProbeAuthIdOrderByCompletedOnAsc(probeAuthId));
    }

    private List<MeasurementDTO> entitiesToDTOs(final Collection<Measurement> entities) {
        List<MeasurementDTO> dtos = new ArrayList<>(entities.size());
        for (Measurement entity : entities) {
            MeasurementDTO dto = new MeasurementDTO();
            dto.setCompletedOn(entity.getCompletedOn());
            dto.setDownloadSpeed(entity.getDownloadSpeed());
            dto.setUploadSpeed(entity.getUploadSpeed());
            dto.setLat(entity.getLatitude());
            dto.setLng(entity.getLongitude());
            dtos.add(dto);
        }
        return dtos;
    }

}
