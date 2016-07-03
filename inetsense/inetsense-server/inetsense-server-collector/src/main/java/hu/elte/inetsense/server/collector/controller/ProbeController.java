package hu.elte.inetsense.server.collector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Probe;

@RestController
@RequestMapping
public class ProbeController {

    @Autowired
    private ProbeRepository probeRepository;

    @RequestMapping("/probe/{probeId}")
    public ResponseEntity<Void> processMessage(@PathVariable String probeId) {
        Probe probe = probeRepository.findOneByAuthId(probeId);
        if(probe != null) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
