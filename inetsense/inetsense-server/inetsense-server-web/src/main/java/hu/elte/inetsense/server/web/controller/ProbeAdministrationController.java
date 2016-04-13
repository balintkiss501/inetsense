/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.server.web.controller;


import hu.elte.inetsense.domain.ProbeRepository;
import hu.elte.inetsense.domain.entities.Probe;
import hu.elte.inetsense.service.ProbeService;
import hu.elte.inetsense.web.dtos.ProbeDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Probe;
import hu.elte.inetsense.server.web.service.ProbeService;

/**
 *
 * @author Bekfi Richárd
 */
@RestController
@RequestMapping("/probe")
public class ProbeAdministrationController {

    @Autowired
    ProbeService    service;

    @Autowired
    ProbeRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List<ProbeDTO> list() {
        return repo.findAll().stream().map(ProbeDTO::new ).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Probe addProbe() {

        return service.addProbe();
    }


}
