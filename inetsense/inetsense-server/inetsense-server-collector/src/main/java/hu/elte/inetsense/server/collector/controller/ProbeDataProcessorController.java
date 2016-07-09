package hu.elte.inetsense.server.collector.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.ProbeDataService;

/**
 * JSON message end point for the Standardized Interface.
 */
@RestController
@RequestMapping("/message-endpoint")
public class ProbeDataProcessorController {

    private final Logger log = LoggerFactory.getLogger(ProbeDataProcessorController.class);

    @Autowired
    private ProbeDataService probeDataService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> processMessage(@Valid @RequestBody ProbeDataDTO probeDataDTO) {
        probeDataService.saveProbeData(probeDataDTO);
        return ResponseEntity.ok("HTTP 200: Incoming JSON message validation and data saving was successfull.");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private String handleException(Exception e) {
        log.error("Processing JSON message has failed :\n" + e.getMessage());
        return e.getMessage();
    }
}
