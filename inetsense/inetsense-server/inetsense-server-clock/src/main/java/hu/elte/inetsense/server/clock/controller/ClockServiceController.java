package hu.elte.inetsense.server.clock.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Clock controller for synchronization between collector server
 * and probes.
 */
@RestController
@RequestMapping("/time")
public class ClockServiceController {

    /**
     * Returns the current time upon HTTP request.
     *
     * @return  current time
     */
    @RequestMapping(method = RequestMethod.GET)
    public Date processMessage() {
        return new Date();
    }
}
