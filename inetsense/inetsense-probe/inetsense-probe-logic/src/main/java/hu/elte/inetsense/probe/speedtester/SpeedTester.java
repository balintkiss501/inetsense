
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

    public SpeedTester( DataUploader du, long millis ) {
        super();

        this.du = du;
        this.interval = millis;
        this.date = new Date();
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
      while(true){
      			SpeedMeter sp = new SpeedMeter("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_50mb.mp4","http://192.168.0.17:8888",5000,(long) 20000,1000000);
      			sp.run();

            du.addMeasurement(new Measurement(
                    this.date.getTime(),
                    sp.getAverageDownloadSpeed(), // max 15Mb
                    sp.getAverageUploadSpeed()  // max  5Mb
                ));
      }

    }

}
