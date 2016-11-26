package hu.elte.inetsense.server.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("inetsense.project")
public class VersionInfo {

    private String version;
    private String builddate;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBuilddate() {
        return builddate;
    }

    public void setBuilddate(String buildDate) {
        this.builddate = buildDate;
    }

}
