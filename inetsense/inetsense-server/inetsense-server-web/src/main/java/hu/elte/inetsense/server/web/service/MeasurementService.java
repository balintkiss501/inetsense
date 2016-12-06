package hu.elte.inetsense.server.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.server.data.converter.MeasurementConverter;
import hu.elte.inetsense.server.data.entities.Measurement;
import hu.elte.inetsense.server.data.entities.Probe;
import hu.elte.inetsense.server.data.repository.MeasurementRepository;
import hu.elte.inetsense.server.data.repository.ProbeRepository;
import hu.elte.inetsense.server.web.util.UserUtils;

/**
 * Created by riboczki on 2016. 11. 05.
 */

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private ProbeRepository probeRepository;
    @Autowired
    private MeasurementConverter measurementConverter;

    public List<MeasurementDTO> getMeasurementsDataByProbeAuthId(String probeAuthId) {
        Optional<Probe> probe =  probeRepository.findOneByAuthId(probeAuthId);
        if (!probe.isPresent() || !validateProbeForCurrentUser(probe.get().getAuthId())) {
            throw new AuthorizationServiceException("Probe authentication token is invalid!");
        }
        Long probeId = probe.get().getId();
		List<Measurement> measurementsForProbe = measurementRepository.findAllByProbeId(probeId);
		return measurementConverter.convertToDtoList(measurementsForProbe);
    }

    private List<Probe> getProbesForCurrentUser() {
        return probeRepository.findAllByUserId(UserUtils.getLoggedInUser().getId());
    }

    private boolean validateProbeForCurrentUser(String probeAuthId) {
        List<Probe> probesForUser = getProbesForCurrentUser();
        for (Probe probe : probesForUser) {
            if (probeAuthId.equals(probe.getAuthId())) return true;
        }
        return false;
    }

}
