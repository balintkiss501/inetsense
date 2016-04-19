package hu.elte.inetsense.probe.speedtester;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

//Bit/sec ban merjuk a letoltesi sebesseg
//Bit/sec ban merjuk a feltoltesi sebesseg

public class SpeedMeter extends Thread {
	/**
	 * Url amint keresztul merni szeretnenk a letoltesi sebesseget
	 */
	String url;
	
    private Random rand;
	

	/**
	 * Atlagos letoltesi sebesseg
	 */
	int averageDownloadSpeed;
	
	/**
	 * Az aktualisan letoltott adat merete
	 */
	int downloadedSize;
	
	/**
	 * A file telljes merete
	 */
	int fileSize;
	
	/**
	 * A letoltes kezdete ota eltelt ido
	 */
	long elapsedTime;
	
	/**
	 * Ido ami utan szakitsa meg a letoltest
	 */
	Integer timeout;
	
	/**
	 * A minimum file meret byte-ban megadva
	 */
	Long minFileSize;
	  
	/**
	 * 
	 * @param url Url ahonnan toltson le
	 * @param timeout Ido ami utan fejezze be a letoltest millisecundumban megadva
	 * @param minFileSize A letoltendo file legkisebb merete byte-ban megadva; 
	 */
	public SpeedMeter(String url,Integer timeout,Long minFileSize){
		this.url = url;
		this.timeout = timeout;
		this.minFileSize = minFileSize;
        this.rand = new Random();
	}
	
	/**
	 * Kulon szalon a letoltes megkezdese
	 */
	public void run(){
		try {
			startDownload();
			//startUpload();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};

	
	/**
	 * A letoltendo file meretevel ter vissza
	 * @return
	 */
	public Integer getFileSize(){
		return this.fileSize;
	}
	
	/**
	 * A mar eddig letoltott meretet adja vissza
	 * @return
	 */
	public Integer getDownloadedSize(){
		return this.downloadedSize;
	}
	
	/**
	 * A letoltes elindult-e mar
	 * @return
	 */
	public Boolean isStarted(){
		return downloadedSize > 0;
	}
	
	
	/**
	 * Az atlagos letoltesi sebesseget adja vissza
	 * @return
	 */
	public Integer getAverageDownloadSpeed(){
		if(isStarted() && (elapsedTime/1000)>0){
			return  (int) ((downloadedSize*8) / (elapsedTime/1000));
		}else{
			return 0;
		}
	}
		
	/**
	 * Folyamatban van-e a toltes
	 * @return
	 */
	public Boolean isDownloaded(){
		if(isStarted()){
			return (downloadedSize == fileSize) || (this.elapsedTime > this.timeout);
		}else {
			return false;
		}
	}
	
	/**
	 * A letoltes elinditasa
	 * @throws IOException 
	 * @throws ParseException
	 */
	public void startDownload() throws IOException, ParseException{
		long startTime = getCurrentTime();
		URL url = new URL(this.url.toString());
	    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	    this.fileSize = urlConnection.getContentLength();
	    System.out.println(urlConnection.getContentLength());
	    if(urlConnection.getContentLength() < this.minFileSize){
	    	//Barmi mas
	    	System.out.println("Warning A fajl merete tul kicsi!");
	    }
	      try {
	        	  InputStream in = urlConnection.getInputStream();

	        	  byte[] b = new byte[4096];
	              int count;
	              while (timeout >= elapsedTime && (count = in.read(b)) > 0)
	              {
	            	  elapsedTime = getCurrentTime() - startTime;
	            	  downloadedSize+=count;
	           // 	  System.out.println(downloadedSize);
	              }
	      }finally {
	    	  	 System.out.println("Downloaded");
	    	     urlConnection.disconnect();
	      }
	
	}
	
	
	/**
	 * A feltoltes elinditasa (Nem mukodik meg)
	 * @throws IOException
	 */
	public void startUpload() throws IOException{
		URL u = new URL("http://192.168.0.17:8888");
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		OutputStream os = conn.getOutputStream();
		byte[] b = new byte[5000000];
			os.write(b);
			os.flush();
			System.out.println(conn.getResponseCode());
			System.out.println("END");
	}
	
	public Integer getAverageUploadSpeed(){
		return this.rand.nextInt( 5*1024*1024*8);
	}
	/**
	 * Az aktualis ido timestampben
	 * @return
	 */
	private long getCurrentTime(){
		Calendar calendar = Calendar.getInstance();	  
		return calendar.getTime().getTime();
	}	
}
