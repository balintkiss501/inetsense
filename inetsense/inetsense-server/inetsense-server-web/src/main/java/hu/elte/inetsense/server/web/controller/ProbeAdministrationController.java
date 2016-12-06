/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.server.web.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.common.dtos.probe.ProbeDTO;
import hu.elte.inetsense.server.common.exception.InetsenseServiceException;
import hu.elte.inetsense.server.data.converter.ProbeConverter;
import hu.elte.inetsense.server.data.entities.probe.Probe;
import hu.elte.inetsense.server.web.service.ProbeService;

/**
 *
 * @author Bekfi Richárd
 */
@RestController
@RequestMapping("/probes")
public class ProbeAdministrationController {

    private final Logger log = LogManager.getLogger();

    @Autowired
    private ProbeService service;
    
    private ProbeConverter probeConverter;

    @RequestMapping(value = "/listForAdmin", method = RequestMethod.GET)
    public List<ProbeDTO> listForAdmin() {
        List<Probe> probes = service.getAllProbes();

        return probeConverter.convertToDtoList(probes);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ProbeDTO> list() {
        List<Probe> probes = service.getAllProbesOfCurrentUser();

        return probeConverter.convertToDtoList(probes);
    }

    @RequestMapping(method = RequestMethod.POST, params = { "authId" })
    public ProbeDTO addProbe(@RequestParam(value = "authId") final String authId) throws InetsenseServiceException {
        Probe probe = service.addProbe(authId);
        return probeConverter.convertToDto(probe);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProbeDTO addProbe() throws InetsenseServiceException {
        Probe probe = service.addProbe();
        return probeConverter.convertToDto(probe);
    }

    @RequestMapping(method = RequestMethod.POST, params = { "authId", "newId" })
    public ProbeDTO changeProbe(@RequestParam(value = "authId") final String authId,
            @RequestParam(value = "newId") final String newId) {
        Probe probe = service.changeProbe(authId, newId);
        return probeConverter.convertToDto(probe);
    }

    @RequestMapping(value = "/probeCountLimit", method = RequestMethod.GET)
    public int getProbeCountLimit() {
        return service.getProbeCountLimit();
    }

    @ExceptionHandler(InetsenseServiceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private String handleInetsenseServiceException(final InetsenseServiceException e) {
        log.error(e.getMessage(), e);
        return e.getMessage();
    }
}
