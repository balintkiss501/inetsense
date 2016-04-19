
package hu.elte.inetsense.probe.speedtester;

import hu.elte.inetsense.probe.uploader.DataUploader;
import hu.elte.inetsense.probe.uploader.Measurement;

import java.util.Date;


    /*********************************************\
     *                                           *
     *                 INS-11                    *
     *                                           *
    \*********************************************/


public class SpeedTester extends Thread {
    
    private DataUploader du;
    private long interval;
    private Date date;
    
    private boolean running = true;
    private SpeedMeter sp;

    public SpeedTester( DataUploader du, long millis ) {
        super();
        
        this.du = du;
        this.interval = millis;
        this.date = new Date();
        this.sp = new SpeedMeter("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_50mb.mp4",100000,(long) 1000000);
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
            
            // save upload/download speed
        	if(sp.isStarted()){
                du.addMeasurement(new Measurement(
                        this.date.getTime(),
                        sp.getAverageDownloadSpeed(), // max 15Mb
                        sp.getAverageUploadSpeed()  // max  5Mb
                    ));
        	}
            try {
                sleep(this.interval);
            } catch (Exception ex) {}
            
        }
    }
    
}
