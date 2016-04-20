
package hu.elte.inetsense.probe;

import hu.elte.inetsense.probe.speedtester.SpeedTester;
import hu.elte.inetsense.probe.uploader.ConfigLoader;
import hu.elte.inetsense.probe.uploader.DataUploader;

/**
 * 
 * Entry point.
 * 
 * @author Adam Kecskes
 */
public class InetsenseProbe {

    
    public static void main(String[] args) {
        
        ConfigLoader cf = null;
        
        for(int i=0; i<args.length; i++) {
            
            if(args[i].equals("-config")) {
                cf = new ConfigLoader(args[i+1]);
            }
            
        }
        
        if(cf == null) {
            cf = new ConfigLoader();
        }
        
        DataUploader du = new DataUploader(cf.get("host")+":"+cf.get("port")+"/message-endpoint" ,cf.get("probe-id"));
        SpeedTester se = new SpeedTester(du, Integer.parseInt(cf.get("test-interval")));
        
        se.start();
        
    }
    
}
