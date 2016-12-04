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
        log.info("Updating ISP information...");
        try {
            isp = checkIsp();
            log.info("Updating done. ISP: {}", isp);
        } catch (Exception e) {
            log.error(e);
        }
        
    } 
    
    /**
     * @throws java.io.IOException
     */
    private String checkIsp() throws IOException {
        try(Scanner scanner = new Scanner(new URL("http://ipinfo.io/org").openStream(), "UTF-8")) {
        	scanner.useDelimiter("\\A");
        	String info = scanner.next();
        	return info.substring(info.indexOf(" "));
        }
        
    }
    
}