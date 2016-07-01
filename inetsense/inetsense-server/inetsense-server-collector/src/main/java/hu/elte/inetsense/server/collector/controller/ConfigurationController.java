package hu.elte.inetsense.server.collector.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.server.data.ConfigurationRepository;
import hu.elte.inetsense.server.data.entities.Configuration;

@RestController
@RequestMapping("/configuration.properties")
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String processMessage() {
        List<Configuration> configList = configurationRepository.findAll();
        return configList.stream().map(Configuration::toString).collect(Collectors.joining("\n"));
    }
}
