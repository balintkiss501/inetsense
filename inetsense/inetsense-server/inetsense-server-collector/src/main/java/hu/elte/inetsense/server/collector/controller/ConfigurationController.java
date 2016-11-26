package hu.elte.inetsense.server.collector.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.server.service.configuration.ServerConfigurationProvider;

@RestController
@RequestMapping("/configuration.properties")
public class ConfigurationController {

    @Autowired
    private ServerConfigurationProvider configurationProvider;

    @RequestMapping(method = RequestMethod.GET)
    public String processMessage() {
        List<String> configList = configurationProvider.getKeys();//configurationProvider.getInt(ConfigurationNames.PROBE_DOWNLOAD_MAX_TIME)
        return configList.stream().map(s->getConfigString(s)).collect(Collectors.joining("\n"));
    }

    private String getConfigString(String key) {
        String value = configurationProvider.getString(key);
        return String.format("%s = %s", key, value);
    }
}
