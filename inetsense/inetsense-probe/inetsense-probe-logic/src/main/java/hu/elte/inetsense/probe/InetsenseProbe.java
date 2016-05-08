
package hu.elte.inetsense.probe;

import org.apache.log4j.Logger;
import hu.elte.inetsense.probe.speedtester.SpeedTester;
import hu.elte.inetsense.probe.uploader.*;


public class InetsenseProbe {

    public static Logger log = Logger.getLogger(InetsenseProbe.class.getName());
    
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
        
        log.info("Config loaded");
        
        DataUploader du = new DataUploader(cf.get("host")+":"+cf.get("port")+"/message-endpoint" ,cf.get("probe-id"));
        SpeedTester se = new SpeedTester(
          du,
          Integer.parseInt(cf.get("test-interval")),
          cf.get("download-location"),
          cf.get("upload-location")
        );

        se.start();

    }

}
