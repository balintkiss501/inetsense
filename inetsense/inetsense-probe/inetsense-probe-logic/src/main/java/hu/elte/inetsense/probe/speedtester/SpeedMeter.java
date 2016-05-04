package hu.elte.inetsense.probe.speedtester;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Scanner;

//Bit/sec ban merjuk a letoltesi sebesseg
//Bit/sec ban merjuk a feltoltesi sebesseg

public class SpeedMeter {

	private String uploadUrl;

	/**
	 * Url amint keresztul merni szeretnenk a letoltesi sebesseget
	 */
	private String downloadUrl;

	/**
	 * A feltoltendo file merete byte-ban
	 */
	private int uploadFileSize;

	/**
	 * Atlagos feltoltesi sebesseg
	 */
	private int averageUploadSpeed;

	/**
	 * A file fel lett-e mar toltve
	 */
	private volatile Boolean uploaded;

	/**
	 * Letoltodott e mar a file
	 */
	private volatile Boolean downloaded;
	/**
	 * Atlagos letoltesi sebesseg
	 */
	private int averageDownloadSpeed;

	/**
	 * Az aktualisan letoltott adat merete
	 */
	private int downloadedSize;

	/**
	 * A file telljes merete
	 */
	private int fileSize;

	/**
	 * A letoltes kezdete ota eltelt ido
	 */
	private long elapsedTime;

	/**
	 * Ido ami utan szakitsa meg a letoltest
	 */
	private Long timeout;

	/**
	 * A minimum file meret byte-ban megadva
	 */
	private Long minFileSize;

	/**
	 *
	 * @param url Url ahonnan toltson le
	 * @param timeout Ido ami utan fejezze be a letoltest millisecundumban megadva
	 * @param minFileSize A letoltendo file legkisebb merete byte-ban megadva;
	 */
	public SpeedMeter(String downloadUrl,String uploadUrl,Long timeout,Long minFileSize,int uplodaedFileSize){
		this.downloadUrl = downloadUrl;
		this.uploadUrl = uploadUrl;
		this.timeout = timeout;
		this.minFileSize = minFileSize;
		this.uploadFileSize = uplodaedFileSize;
		this.uploaded = false;
		this.downloaded = false;
	}

	/**
	 * A letöltés megkezdése
	 */
	public void run(){
		try {
			startDownload();
			startUpload(this.uploadFileSize);
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
	public Boolean isDownloadStarted(){
		return downloadedSize > 0;
	}


	/**
	 * Az atlagos letoltesi sebesseget adja vissza
	 * @return
	 */
	public Integer getAverageDownloadSpeed(){
		if(isDownloadStarted() && (elapsedTime/1000)>0){
			return  (int) ((downloadedSize*8) / (elapsedTime/1000));
		}else{
			return 0;
		}
	}

	/**
	 * Atlagos feltoltesi sebesseg
	 * @return
	 */
	public Integer getAverageUploadSpeed(){
		return this.averageUploadSpeed;
	}

	/**
	 * Folyamatban van-e a feltöltés
	 */
	public Boolean isUploaded(){

		return this.uploaded;
	}

	/**
	 * Folyamatban van-e a toltes
	 * @return
	 */
	public Boolean isDownloaded(){
		return this.downloaded;
	}

	/**
	 * A letoltes elinditasa
	 * @throws IOException
	 * @throws ParseException
	 */
	public void startDownload() throws IOException, ParseException{
		long startTime = getCurrentTime();
		URL url = new URL(this.downloadUrl.toString());
	    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	    this.fileSize = urlConnection.getContentLength();
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
	              }
	      }finally {
	    	  	 this.downloaded = true;
	    	     urlConnection.disconnect();
	      }

	}


	/**
	 * A feltoltes elinditasa (Nem mukodik meg)
	 * @throws IOException
	 */
	public void startUpload(int fileSize) {
		HttpURLConnection conn = null;
		try {

		URL u = new URL(this.uploadUrl);
		conn = (HttpURLConnection) u.openConnection();

		conn.setDoOutput(true);
		conn.setRequestMethod("POST");

		OutputStream os = conn.getOutputStream();

		byte[] b = new byte[fileSize];

		b[b.length-1] = '\n';
		os.write(b);
		os.flush();
		Scanner in = new Scanner(conn.getInputStream());
		byte[] inread = new byte[4096];
		int receiveCount = 0;

		String line = in.nextLine();
			this.averageUploadSpeed = ((fileSize * 8) / Integer.parseInt(line))*1000;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.uploaded = true;
			conn.disconnect();
		}

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
