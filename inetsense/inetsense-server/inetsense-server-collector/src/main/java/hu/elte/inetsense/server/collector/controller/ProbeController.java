package hu.elte.inetsense.server.collector.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.server.data.ProbeRepository;

@RestController
@RequestMapping("/probe")
public class ProbeController {

    @Autowired
    private ProbeRepository probeRepository;

    @RequestMapping("/{probeId}")
    public ResponseEntity<Void> processMessage(@PathVariable String probeId) {
        probeRepository.findOneByAuthId(probeId).get();
        return ResponseEntity.ok(null);
    }
    
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private String handleException(Exception e) {
        return e.getMessage();
    }
}
