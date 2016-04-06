
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
    private SpeedMeter speedMeter;
    
    private boolean running = true;

    public SpeedTester( DataUploader du, long millis ) {
        
        super();
        this.speedMeter = new SpeedMeter("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_50mb.mp4",10000,(long) 1000000);
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
    	this.speedMeter.start();
        while(!this.speedMeter.isDownloaded()) {
           // System.out.println(this.speedMeter.getAverageDownloadSpeed());
            // save upload/download speed
            du.addMeasurement(new Measurement(
                this.date.getTime(),
                this.speedMeter.getAverageDownloadSpeed(), // max 15Mb
                this.rand.nextInt( 5*1024*1024*8)  // max  5Mb
            ));

            try {
                sleep(this.interval);
            } catch (Exception ex) {}
            
        }
    }
    
}
