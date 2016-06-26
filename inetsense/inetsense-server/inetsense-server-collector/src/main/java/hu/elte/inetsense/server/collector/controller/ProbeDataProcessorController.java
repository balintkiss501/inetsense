package hu.elte.inetsense.server.collector.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.common.util.JsonConverter;
import hu.elte.inetsense.server.collector.service.ProbeDataService;
import hu.elte.inetsense.server.collector.service.ProbeDataValidator;

/**
 * JSON message end point for the Standardized Interface.
 */
@RestController
@RequestMapping("/message-endpoint")
public class ProbeDataProcessorController {

    private final Logger log = LoggerFactory.getLogger(ProbeDataProcessorController.class);

    @Autowired
    private ProbeDataValidator validator;

    @Autowired
    private JsonConverter jsonConverter;

    @Autowired
    private ProbeDataService probeDataService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> processMessage(@RequestBody String message) {

        try {
            ProbeDataDTO probeDataDTO = jsonConverter.json2Object(message, ProbeDataDTO.class);
            validator.validate(probeDataDTO);
            probeDataService.saveProbeData(probeDataDTO);
        } catch (Exception e) {
            log.error("Incoming JSON message validation has failed due to this reason:\n" + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("HTTP 500 error: Incoming JSON message validation has failed due to this reason:\n"
                            + e.getMessage()); // TODO: adding Spring's default
                                               // JSON-based response
        }

        return ResponseEntity.ok("HTTP 200: Incoming JSON message validation and data saving was successfull."); 
    }
}
