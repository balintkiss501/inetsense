package hu.elte.inetsense.probe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hu.elte.inetsense.probe.service.EnvironmentService;

@Configuration
@ComponentScan(value = "hu.elte.inetsense.probe")
public class ProbeConfiguration {

    // HACK: EnvironmentService should be created first
    @SuppressWarnings("unused")
    @Autowired
    private EnvironmentService environmentService;
}
