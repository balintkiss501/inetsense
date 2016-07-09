package hu.elte.inetsense.server.collector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.server.collector.service.impl.CollectorConfigurationProvider;

@Controller
@RequestMapping("/Inetsense-Client")
@EnableWebMvc
@ComponentScan(basePackageClasses = JWSClientController.class)
public class JWSClientController {

    @Autowired
    private CollectorConfigurationProvider configurationProvider;

    @RequestMapping(method = RequestMethod.GET)
    public String processMessage(Model model) {
        model.addAttribute("version", configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_VERSION));
        model.addAttribute("built", configurationProvider.getString(ConfigurationNames.INETSENSE_PROJECT_BUILD_DATE));
        return "inetsense-client"; 
    }
}
