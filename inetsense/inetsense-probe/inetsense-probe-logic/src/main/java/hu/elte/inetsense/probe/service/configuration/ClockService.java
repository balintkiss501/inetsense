package hu.elte.inetsense.probe.service.configuration;

import java.util.Date;
import java.util.Scanner;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.probe.service.util.HTTPUtil;

public class ClockService {
    
    private static Logger log = LogManager.getLogger();

    private ConfigurationProvider configurationProvider;

    private Long diffToRemoteClock = null;

    public ClockService(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    public Date getCurrentTime() {
        return getCurrentTimeModified();
    }

    private Date getCurrentTimeModified() {
        if (diffToRemoteClock == null) {
            refreshClock();
        }
        return new Date(System.currentTimeMillis() + diffToRemoteClock);
    }

    public void refreshClock() {
        try {
            Date remoteDate = getRemoteDate();
            diffToRemoteClock = remoteDate.getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            fallBackToSystemClock();
            log.error(e);
        }
    }

    private Date getRemoteDate() throws Exception {
        CloseableHttpResponse response = HTTPUtil.get(getServerURL());
        try (Scanner sc = new Scanner(response.getEntity().getContent()) ){
            String s = sc.nextLine();
            return new Date(Long.parseLong(s));
        }
    }

    private void fallBackToSystemClock() {
        if (diffToRemoteClock == null) {
            diffToRemoteClock = 0L;
        }
    }

    private String getServerURL() {
        return String.format("%s/time", configurationProvider.getCollectorBaseURL());
    }

}
