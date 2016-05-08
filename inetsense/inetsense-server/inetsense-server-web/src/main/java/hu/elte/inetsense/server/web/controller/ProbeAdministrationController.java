/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.server.web.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.common.dtos.ProbeDTO;
import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Probe;
import hu.elte.inetsense.server.web.config.ExtJSCORS;
import hu.elte.inetsense.server.web.service.ProbeService;

/**
 *
 * @author Bekfi Richárd
 */
@RestController
@CrossOrigin(origins = ExtJSCORS.EXTJS_LOCAL)
@RequestMapping("/probes")
public class ProbeAdministrationController {

    @Autowired
    ProbeService service;

    @Autowired
    ProbeRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProbeDTO> list() {
        return repo.findAll().stream().map(p->{return new ProbeDTO(p.getAuthId(), p.getCreatedOn());} ).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProbeDTO addProbe() {
        Probe p = service.addProbe();
        return new ProbeDTO(p.getAuthId(), p.getCreatedOn());
    }


}
