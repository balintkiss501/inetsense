
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
    private String uploadLocation;
    private String downloadLocation;

    private boolean running = true;

    public SpeedTester( DataUploader du, long millis , String downloadLocation, String uploadLocation ) {
        super();

        this.uploadLocation = uploadLocation;
        this.downloadLocation = downloadLocation;
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
      			SpeedMeter sp = new SpeedMeter("http://"+this.downloadLocation,"http://"+this.uploadLocation,this.interval,(long) 20000,1000000);
      			sp.run();

            du.addMeasurement(new Measurement(
                    this.date.getTime(),
                    sp.getAverageDownloadSpeed(), // max 15Mb
                    sp.getAverageUploadSpeed()  // max  5Mb
            ));
      }

    }

}
