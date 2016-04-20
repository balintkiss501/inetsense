package hu.elte.inetsense.server.collector.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
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

    private final Logger          log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProbeRepository       probeRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    @Transactional(readOnly = false)
    public void saveProbeData(final ProbeDataDTO probeData) {
        Probe probe = this.probeRepository.findOneByAuthId(probeData.getProbeAuthId());
        if (probe == null) {
            log.warn("ProbeData save skipped because Probe does not exist with auth ID: " + probeData.getProbeAuthId());
            return;
        }

        for (MeasurementDTO measurementDTO : probeData.getMeasurements()) {
            Measurement measurement = new Measurement();

            measurement.setCreatedOn(new Date());
            measurement.setProbe(probe);
            measurement.setCompletedOn(measurementDTO.getCompletedOn());
            measurement.setDownloadSpeed(measurementDTO.getDownloadSpeed());
            measurement.setUploadSpeed(measurementDTO.getUploadSpeed());
            measurement.setLatitude(measurementDTO.getLat());
            measurement.setLongitude(measurementDTO.getLng());

            this.measurementRepository.save(measurement);
        }
    }

}
