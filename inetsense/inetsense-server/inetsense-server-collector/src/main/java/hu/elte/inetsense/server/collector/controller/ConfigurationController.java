package hu.elte.inetsense.server.collector.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.server.collector.util.VersionInfo;
import hu.elte.inetsense.server.data.ConfigurationRepository;
import hu.elte.inetsense.server.data.entities.Configuration;

@RestController
@RequestMapping("/configuration.properties")
public class ConfigurationController {

    @Autowired
    private ConfigurationRepository configurationRepository;
    
    @Autowired
    private VersionInfo versionInfo;

    @RequestMapping(method = RequestMethod.GET)
    public String processMessage() {
        List<Configuration> configList = configurationRepository.findAll();
        addVersionToConfig(configList);
        return configList.stream().map(Configuration::toString).collect(Collectors.joining("\n"));
    }

    private void addVersionToConfig(List<Configuration> configList) {
        configList.add(create("inetsense.project.version", versionInfo.getVersion()));
        configList.add(create("inetsense.project.build", versionInfo.getBuilddate()));
    }

    private Configuration create(String key, String value) {
        Configuration config = new Configuration();
        config.setKey(key);
        config.setValue(value);
        return config;
    }
}
