package hu.elte.inetsense.upload.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.util.InetsenseUtil;
import hu.elte.inetsense.probe.service.configuration.ConfigurationNames;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;

@Component
public class UploadServer {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private ConfigurationProvider configurationProvider;
    
    private ExecutorService executor = Executors.newFixedThreadPool(150);

    private class UploadTask implements Callable<Void> {
        private Socket socket;

        public UploadTask(Socket socket) {
            this.socket = socket;
        }

        public Void call() throws Exception{
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            long downloadSpeed = processData(stopWatch, socket);

            PrintWriter out = new PrintWriter(socket.getOutputStream());

            System.out.println("End!");
            out.println("HTTP/1.0 200 OK");
            out.println("Content-Type: text/html");
            out.println("Server: Bot");
            out.println("");

            out.println(downloadSpeed);
            out.flush();
            socket.close();
            log.info("Upload processed. Speed: {} Bps. Process time: {}", downloadSpeed, stopWatch.toString());
            return null;
        }
    }
    
    public void start(int port) throws Exception {
        ServerSocket s = new ServerSocket(port);

        for (;;) {
            Socket socket = s.accept();
            executor.submit(new UploadTask(socket));
        }
    }


    private long processData(StopWatch stopWatch, Socket socket) throws IOException {
        int timeout = configurationProvider.getInt(ConfigurationNames.PROBE_DOWNLOAD_MAX_TIME);
        int fileSize = configurationProvider.getInt(ConfigurationNames.PROBE_UPLOAD_SIZE);
        byte[] b = new byte[fileSize];
        int count;
        long downloadedSize = 0;
        InputStream in = socket.getInputStream();
        while (timeout > stopWatch.getTime() && (count = in.read(b)) > 0 && b[count - 1] != 10) {
            downloadedSize += count;
        }
        stopWatch.stop();
        long downloadSpeed = InetsenseUtil.calculateSpeed(downloadedSize, stopWatch.getTime());
        return downloadSpeed;
    }

}
