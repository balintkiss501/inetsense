package hu.elte.inetsense.probe.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.common.service.configuration.BaseConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;

public class UploadSpeedMeterService implements SpeedMeterService {

    private static final Logger log = LogManager.getLogger();
    private BaseConfigurationProvider configurationProvider;

    public UploadSpeedMeterService(BaseConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    @Override
    public long measure() throws Exception {
        return upload();
    }

    private long upload() throws Exception {
    	 log.info("Measuring upload speed...");
         try (Socket socket = createUploadSocket()) {
        	 sendUploadData(socket);
        	 return readUploadSpeed(socket);
         }
    }

	private long readUploadSpeed(Socket socket) throws IOException {
		try(Scanner sc = new Scanner(socket.getInputStream())) {
             if(sc.hasNextLong()) {
                 long nextLong = sc.nextLong();
                 log.info("Upload done. Measured speed: {} Bps.", nextLong);
                 return nextLong;
             }
         }
		return 0;
	}

	private void sendUploadData(Socket socket) throws IOException {
		OutputStream os = socket.getOutputStream();
         os.write(createBinaryData());
         os.flush();
	}

	private Socket createUploadSocket() throws UnknownHostException, IOException {
		String host = configurationProvider.getString(ConfigurationNames.UPLOAD_SERVER_HOST);
		 int port = configurationProvider.getInt(ConfigurationNames.UPLOAD_SERVER_PORT);
		 Socket socket = new Socket(host , port);
		return socket;
	}

    private byte[] createBinaryData() {
        int fileSize = configurationProvider.getInt(ConfigurationNames.PROBE_UPLOAD_SIZE);
        log.info("...Using upload data size: {} bytes.", fileSize);
        byte[] b = new byte[fileSize];
        Arrays.fill(b, (byte) 1);
        b[b.length-1] = '\n';
        return b;
    }

}
