package hu.elte.inetsense.server.upload;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class Main {

	  static HashMap<String,String> headerValue;
	  public static Map<String,HashMap<String,Socket>> homeUpdate = new HashMap<String,HashMap<String,Socket>>();

	  public static Map<String,Socket> userUpdate = new HashMap<String,Socket>();

	  private static final int port = 8888;

	  protected void start() throws IOException {
	    ServerSocket s;

	    System.out.println("Webserver starting up on port: " + port);
	    System.out.println("(press ctrl-c to exit)");
	    try {
	      // create the main server socket
	      s = new ServerSocket(port);
	    } catch (Exception e) {
	      System.out.println("Error: " + e);
	      return;
	    }

	    System.out.println("Waiting for connection");
	    for (;;) {
	        // wait for a connection
	        System.out.println("Waiting for connection");
	        Socket remote = s.accept();

	        System.out.println("Connected");
	        InputStream in = remote.getInputStream();

      	  	byte[] b = new byte[100000];
      	  	int count;
      	  	long downloadedSize = 0;
      	  	System.out.println("csa");
      	  	long start = getCurrentTime();
	        while ((count = in.read(b)) > 0 && b[count-1] != 10)
	        {
	            	  downloadedSize+=count;
	            	  System.out.println(downloadedSize);
	            	  System.out.println(b[count-1]);
	        }

	  	     PrintWriter out = new PrintWriter(remote.getOutputStream());

	         System.out.println("End!");
	         out.println("HTTP/1.0 200 OK");
	         out.println("Content-Type: text/html");
	         out.println("Server: Bot");
	       //  out.println("12000");
	            // this blank line signals the end of the headers
	         out.println("");
	      //   System.out.println();

	         out.println(getCurrentTime() - start);
	         out.flush();
	         remote.close();
	    }
	  }

	  private long getCurrentTime(){
		  Calendar calendar = Calendar.getInstance();
		  return calendar.getTime().getTime();
	  }

	  public static void main(String args[]) throws IOException {
	    Main ws = new Main();
	    ws.start();
	  }


}
