package hu.elte.inetsense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.domain.MeasurementRepository;
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
        return dozerAll(measurementRepository.findAll(), MeasurementDTO.class);
    }

}
