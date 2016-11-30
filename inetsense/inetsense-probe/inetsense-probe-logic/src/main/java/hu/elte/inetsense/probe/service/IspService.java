package hu.elte.inetsense.probe.service;

import java.io.IOException;
import java.util.Scanner;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Get ISP name
 */
public class IspService {
     
    private static final Logger log = LogManager.getLogger();
    
    private String isp = null;
    
    public IspService() {}
    
    public String getIspName() {
        return isp;
    }
    
    public void updateIsp() {
        
        log.info("Updating ISP...");
        
        try {
            isp = checkIsp();
            log.info("Updating done. ISP: "+isp);
        } catch (Exception e) {
            log.error(e);
        }
        
    } 
    
    /**
     * @throws java.io.IOException
     */
    private String checkIsp() throws IOException {
        
        String info = new Scanner(new URL("http://ipinfo.io/org").openStream(), "UTF-8").useDelimiter("\\A").next();
        
        // remove ID from the result, to get the ISP name
        return info.substring(info.indexOf(" "));
        
    }
    
}