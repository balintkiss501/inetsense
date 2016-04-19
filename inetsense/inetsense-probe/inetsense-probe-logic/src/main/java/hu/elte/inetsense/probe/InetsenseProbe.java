
package hu.elte.inetsense.probe;

import hu.elte.inetsense.probe.speedtester.SpeedTester;
import hu.elte.inetsense.probe.uploader.DataUploader;

/**
 * 
 * Entry point.
 * 
 * @author Adam Kecskes
 */
public class InetsenseProbe {

    
    public static void main(String[] args) {
        
        DataUploader du = new DataUploader("localhost:8080/message-endpoint" ,"Test id");
        SpeedTester se = new SpeedTester(du, 10000);
        
        se.start();
        
    }
    
}
