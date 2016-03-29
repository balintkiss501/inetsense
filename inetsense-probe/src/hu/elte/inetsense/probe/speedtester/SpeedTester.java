
package hu.elte.inetsense.probe.speedtester;

import hu.elte.inetsense.probe.uploader.DataUploader;
import hu.elte.inetsense.probe.uploader.Measurement;
import java.util.Random;
import java.util.Date;


    /*********************************************\
     *                                           *
     *                 INS-11                    *
     *                                           *
    \*********************************************/


public class SpeedTester extends Thread {
    
    private DataUploader du;
    private long interval;
    private Random rand;
    private Date date;
    
    private boolean running = true;

    public SpeedTester( DataUploader du, long millis ) {
        
        super();
        
        this.du = du;
        this.interval = millis;
        this.date = new Date();
        
        this.rand = new Random();
        
    }
    
    //

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        
        if(!this.running && running) {
            this.running = running;
            this.start();
        } else {
            this.running = running;
        }
        
        
    }
    
    
    //
    
    @Override
    public void run() {
        
        while(this.running) {
            
            du.addMeasurement(new Measurement(
                this.date.getTime(),
                this.rand.nextInt(15*1024*1025), // max 15MB
                this.rand.nextInt( 5*1024*1024)  // max  5MB
            ));

            try {
                sleep(this.interval);
            } catch (Exception ex) {}
            
        }
    }
    
}
