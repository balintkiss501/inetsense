/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.server.web.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import hu.elte.inetsense.common.dtos.ProbeDTO;
import hu.elte.inetsense.server.common.exception.InetsenseServiceException;
import hu.elte.inetsense.server.data.entities.Probe;
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
    ProbeService         service;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProbeDTO> list() {
        List<Probe> probes = service.getAllProbesOfCurrentUser();

        return probes.stream().map(p -> {
            return new ProbeDTO(p.getAuthId(), p.getCreatedOn());
        }).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST, params = { "authId" })
    public ProbeDTO addProbe(@RequestParam(value = "authId") final String authId) throws InetsenseServiceException {
        Probe p = service.addProbe(authId);
        return new ProbeDTO(p.getAuthId(), p.getCreatedOn());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProbeDTO addProbe() throws InetsenseServiceException {
        Probe p = service.addProbe();
        return new ProbeDTO(p.getAuthId(), p.getCreatedOn());
    }

    @RequestMapping(method = RequestMethod.POST, params = { "authId", "newId" })
    public ProbeDTO changeProbe(@RequestParam(value = "authId") final String authId,
            @RequestParam(value = "newId") final String newId) {
        Probe p = service.changeProbe(authId, newId);
        return new ProbeDTO(p.getAuthId(), p.getCreatedOn());
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
