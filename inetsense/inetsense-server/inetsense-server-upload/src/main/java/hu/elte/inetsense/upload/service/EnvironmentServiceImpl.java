package hu.elte.inetsense.upload.service;

import org.springframework.stereotype.Component;

import hu.elte.inetsense.probe.service.configuration.EnvironmentService;

@Component
public class EnvironmentServiceImpl implements EnvironmentService{

    @Override
    public String getConfigurationFilePath() {
        return null;
    }

}
