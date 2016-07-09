package hu.elte.inetsense.server.collector.service.impl;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.ProbeDataService;
import hu.elte.inetsense.server.data.MeasurementRepository;
import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Measurement;
import hu.elte.inetsense.server.data.entities.Probe;

/**
 * @author Zsolt Istvanfi
 */
@Service
@Transactional(readOnly = true)
public class ProbeDataServiceImpl implements ProbeDataService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProbeRepository probeRepository;
    
    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    @Transactional(readOnly = false)
    public void saveProbeData(final ProbeDataDTO probeData) {
        Probe probe = getProbe(probeData.getProbeAuthId());

        for (MeasurementDTO measurementDTO : probeData.getMeasurements()) {
            Measurement measurement = measurementDto2Entity(probe, measurementDTO);
            measurementRepository.save(measurement);
        }
    }

    private Probe getProbe(final String authId) {
        Optional<Probe> optionalProbe = probeRepository.findOneByAuthId(authId);
        Probe probe = optionalProbe.orElseThrow(() -> {
            log.error("Unable to find probe based on auth id. Measurements can be saved only for an existing probe! auth ID: " + authId);
            return new NoSuchElementException("Unable to find probe based on auth id. Measurements can be saved only for an existing probe!");
        });
        return probe;
    }

    private Measurement measurementDto2Entity(Probe probe, MeasurementDTO measurementDTO) {
        Measurement measurement = new Measurement();

        measurement.setCreatedOn(new Date());
        measurement.setProbe(probe);
        measurement.setCompletedOn(measurementDTO.getCompletedOn());
        measurement.setDownloadSpeed(measurementDTO.getDownloadSpeed());
        measurement.setUploadSpeed(measurementDTO.getUploadSpeed());
        measurement.setLatitude(measurementDTO.getLat());
        measurement.setLongitude(measurementDTO.getLng());
        return measurement;
    }

}
