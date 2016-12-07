package hu.elte.inetsense.server.collector.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.common.dtos.probe.ProbeConfigurationDTO;
import hu.elte.inetsense.server.data.converter.ProbeConfigurationConverter;
import hu.elte.inetsense.server.data.entities.probe.ProbeConfiguration;
import hu.elte.inetsense.server.data.repository.ProbeConfigurationRepository;
import hu.elte.inetsense.server.data.repository.ProbeRepository;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    @Autowired
    private ProbeRepository probeRepository;
    
    @Autowired
    private ProbeConfigurationRepository probeConfigurationRepository;
    
    @Autowired
    private ProbeConfigurationConverter probeConfigurationConverter;

    @RequestMapping("/{probeAuthId}")
    public List<ProbeConfigurationDTO> processMessage(@PathVariable String probeAuthId) {
        probeRepository.findOneByAuthId(probeAuthId).get();
        List<ProbeConfiguration> configurationEntites = probeConfigurationRepository.findConfigurationsByProbeAuthId(probeAuthId);
		return probeConfigurationConverter.convertToDtoList(configurationEntites);
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String handleException(Exception e) {
        return e.getMessage();
    }
}
