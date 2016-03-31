
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
        
        DataUploader du = new DataUploader("localhost:8000" ,"Test id");
        SpeedTester se = new SpeedTester(du, 2500);
        
        se.start();
        
    }
    
}
