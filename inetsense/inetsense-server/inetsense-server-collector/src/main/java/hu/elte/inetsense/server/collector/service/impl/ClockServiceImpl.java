package hu.elte.inetsense.server.collector.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hu.elte.inetsense.server.collector.service.ClockService;
import hu.elte.inetsense.server.collector.util.ClockServerProperties;

@Component
public class ClockServiceImpl implements ClockService {

    @Autowired
    private ClockServerProperties clockServerProperties;
    
    private Long diffToRemoteClock = null;
    
    private RestTemplate restTemplate;
    
    @Override
    public Date getCurrentTime() {
        return getCurrentTimeModified();
    }

    private Date getCurrentTimeModified() {
        if(!clockServerProperties.isRemote()) {
            return new Date();
        } else {
            if(diffToRemoteClock == null) {
                refreshClock();
            }
            return new Date(System.currentTimeMillis() + diffToRemoteClock);
        }
    }
    
    @Scheduled(initialDelay = 2 * 60 * 1000, fixedRate = 60 * 60 * 1000)
    public void refreshClock() {
        try {
            Date remoteDate = getRemoteDate();
            diffToRemoteClock = remoteDate.getTime() - System.currentTimeMillis();
        } catch(Exception e) {
            fallBackToSystemClock();
        }
    }

    private Date getRemoteDate() {
        Date date = restTemplate.getForObject(getClockServerURL(), Date.class);
        return date;
    }
    
    private void fallBackToSystemClock() {
        if(diffToRemoteClock == null) {
            diffToRemoteClock = 0L;
        }
    }

    private String getClockServerURL() {
        return String.format("http://%s:%s/time", clockServerProperties.getHost(), clockServerProperties.getPort());
    }
    
    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
        restTemplate.getForObject(getClockServerURL(), Date.class);
    }

}
