
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

	/**
	 * A szerverrel valo kommunikációt valósítja meg
	 */
    private DataUploader du;
    
    /**
     * A letöltési sebesség ideje ms-ben
     */
    private long interval;
    
    /**
     * Az aktuális dátumot adja vissza
     */
    private Date date;
    
    /**
     * A feltöltési szerver címe
     */
    private String uploadLocation;
    
    /**
     * A letöltési szerver címe
     */
    private String downloadLocation;

    /**
     * Fut e még a szál.
     */
    private boolean running = true;

    /**
	 * @param du Az adatgyűjtő szerverre való kommunikációért felelős
	 * @param millis Ido ami utan fejezze be a letoltest millisecundumban megadva
	 * @param downloadLocation A letöltés szerver címe;
	 * @param uploadLocation A feltöltési szerver címe;
     */
    public SpeedTester( DataUploader du, long millis , String downloadLocation, String uploadLocation ) {
        super();

        this.uploadLocation = uploadLocation;
        this.downloadLocation = downloadLocation;
        this.du = du;
        this.interval = millis;
    }

    //

    /**
     * Fut-e még a szál
     */
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

    /**
     * A letöltés és feltöltés mérése ciklukusan, amíg le nem állítják a szálat.
     */
    @Override
    public void run() {
      while(true){
      			SpeedMeter sp = new SpeedMeter("http://"+this.downloadLocation,"http://"+this.uploadLocation,this.interval,(long) 20000,1000000);
      			sp.run();

      	        this.date = new Date();

            du.addMeasurement(new Measurement(
                    this.date.getTime(),
                    sp.getAverageDownloadSpeed(), // max 15Mb
                    sp.getAverageUploadSpeed()  // max  5Mb
            ));
      }

    }

}
