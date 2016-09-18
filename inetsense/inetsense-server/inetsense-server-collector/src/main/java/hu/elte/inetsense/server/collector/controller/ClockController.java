package hu.elte.inetsense.server.collector.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.server.collector.service.ClockService;

@RestController
@RequestMapping("/time")
public class ClockController {

    @Autowired
    private ClockService clockService;

    @RequestMapping(method = RequestMethod.GET)
    public Date processMessage() {
        return clockService.getCurrentTime(); 
    }
}
