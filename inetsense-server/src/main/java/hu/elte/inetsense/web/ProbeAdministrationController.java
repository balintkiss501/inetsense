/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.web;

import hu.elte.inetsense.domain.ProbeRepository;
import hu.elte.inetsense.domain.entities.Probe;
import hu.elte.inetsense.service.ProbeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bekfi Richárd
 */
@RestController
@RequestMapping("/probe")
public class ProbeAdministrationController {

    @Autowired
    ProbeService service;

    @Autowired
    ProbeRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List<Probe> list() {
        return repo.findAll();
    }

    @RequestMapping( method = RequestMethod.POST)
    public Probe addProbe() {
        
        return service.addProbe();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable("id") Long id) {
        Probe entity = repo.findOne(id);
        if (entity != null) {
            repo.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

}
