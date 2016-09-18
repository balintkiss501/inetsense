package hu.elte.inetsense.server.clock.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/time")
public class ClockServiceController {

    @RequestMapping(method = RequestMethod.GET)
    public Date processMessage() {

        Date now = new Date();
        
        return now; 
    }
}
