
package hu.elte.inetsense.probe;

import hu.elte.inetsense.probe.uploader.ConfigLoader;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ConfigTest {
    
    private ConfigLoader cl;
    
    @Before
    public void init() {
        
        // load default config file
        cl = new ConfigLoader();
        
    }
    
    @Test
    public void testServerSet() {
        
        String host = cl.get("host");
        String port = cl.get("port");
        
        if(host == null) {
            fail("Host parameter is not defined in the default config");
        }
        
        if(port == null) {
            fail("Port parameter is not defined in the default config");
        }
        
        if(Integer.parseInt(port) <= 0) {
            fail("Invalid port in the config");
        }
        
        
    }
    
    @Test
    public void testProbeId() {
        
        String probe_id = cl.get("probe-id");
        
        if(probe_id == null) {
            fail("Probe id parameter is not defined in the default config");
        }
        
        if(probe_id.length() != 8) {
            fail("Probe id has to be 8 char long");
        }
        
    }
    
    @Test
    public void testInterval() {
        
        String test_interval = cl.get("test-interval");
        
        if(test_interval == null) {
            fail("Test interval parameter is not defined in the default config");
        }
        
        if(Integer.parseInt(test_interval) <= 0) {
            fail("Test interval is not a valid number");
        }
        
    }
    
    @Test
    public void testLocation() {
        
        String download_location = cl.get("download-location");
        String upload_location = cl.get("upload-location");
        
        if(download_location == null) {
            fail("Download location parameter is not defined in the default config");
        }
        
        if(upload_location == null) {
            fail("Upload location parameter is not defined in the default config");
        }
        
    }
    
}
