package hu.elte.inetsense.server.web.service;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDTO;
import hu.elte.inetsense.server.data.MeasurementRepository;
import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Measurement;
import hu.elte.inetsense.server.data.entities.Probe;
import hu.elte.inetsense.server.web.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by riboczki on 2016. 11. 05.
 */

@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private ProbeRepository probeRepository;

    public List<MeasurementDTO> getMeasurementsDataByProbeAuthId(String probeAuthId) {
        Optional<Probe> foundProbe =  probeRepository.findOneByAuthId(probeAuthId);
        if (!foundProbe.isPresent() || !validateProbeForCurrentUser(foundProbe.get().getAuthId()))
            throw new AuthorizationServiceException("Probe authentication token is invalid!");
        return mapToMeasurementDtoList(measurementRepository.findAllByProbeId(foundProbe.get().getId()));
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

    private List<ProbeDTO> mapToProbeDtoList(List<Probe> probes) {
        return probes.stream()
                .map(probe -> new ProbeDTO(probe.getAuthId(), probe.getCreatedOn()))
                .collect(Collectors.toList());
    }

    private List<MeasurementDTO> mapToMeasurementDtoList(List<Measurement> measurements) {
        return measurements.stream()
                .map(measurement -> new MeasurementDTO(measurement.getCompletedOn(),
                        measurement.getDownloadSpeed(), measurement.getUploadSpeed()))
                .collect(Collectors.toList());
    }
}
