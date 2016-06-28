package hu.elte.inetsense.server.collector.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("inetsense.clockserver")
public class ClockServerProperties {

    private String host;
    private int port;
    private boolean remote;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public boolean isRemote() {
        return remote;
    }
    
    public void setRemote(boolean remote) {
        this.remote = remote;
    }

}
