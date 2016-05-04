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

    static HashMap<String, String>                     headerValue;
    public static Map<String, HashMap<String, Socket>> homeUpdate   = new HashMap<String, HashMap<String, Socket>>();

    public static Map<String, Socket>                  userUpdate   = new HashMap<String, Socket>();

    private static final int                           DEFAULT_PORT = 8888;

    private final int                                  port;

    public Main(final int port) {
        this.port = port;
    }

    protected void start() throws IOException {
        ServerSocket s;

        System.out.println("Webserver starting up on port: " + this.port);
        System.out.println("(press ctrl-c to exit)");
        try {
            // create the main server socket
            s = new ServerSocket(this.port);
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
            while ((count = in.read(b)) > 0 && b[count - 1] != 10) {
                downloadedSize += count;
                System.out.println(downloadedSize);
                System.out.println(b[count - 1]);
            }

            PrintWriter out = new PrintWriter(remote.getOutputStream());

            System.out.println("End!");
            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/html");
            out.println("Server: Bot");
            // out.println("12000");
            // this blank line signals the end of the headers
            out.println("");
            // System.out.println();

            out.println(getCurrentTime() - start);
            out.flush();
            remote.close();
        }
    }

    private long getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getTime();
    }

    public static void main(final String args[]) throws IOException {
        int port = DEFAULT_PORT;
        if (args.length >= 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Warning: port argument is not an integer. Using default port.");
            }
        } else {
            System.out.println("No command line port argument. Using default port.");
        }
        Main ws = new Main(port);
        ws.start();
    }

}
