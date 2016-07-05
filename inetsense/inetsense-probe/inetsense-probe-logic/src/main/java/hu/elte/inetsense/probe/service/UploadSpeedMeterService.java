package hu.elte.inetsense.probe.service;

import java.util.Arrays;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.probe.service.configuration.ConfigurationNames;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;

public class UploadSpeedMeterService implements SpeedMeterService {

    private static final Logger log = LogManager.getLogger();
    private ConfigurationProvider configurationProvider;

    public UploadSpeedMeterService(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    @Override
    public long measure() throws Exception {
        return upload();
    }

    private long upload() throws Exception {
        log.info("Measuring upload speed...");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpEntity entity = MultipartEntityBuilder.create()
                .addBinaryBody("file", createBinaryData(), ContentType.create("application/octet-stream"), "filename")
                .build();

        HttpPost httpPost = new HttpPost(getUploadUrl());
        httpPost.setEntity(entity);
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity result = response.getEntity();
        log.info("Upload server responded with status: {} ", response.getStatusLine().getStatusCode());
        try(Scanner sc = new Scanner(result.getContent())) {
            if(sc.hasNextLong()) {
                return sc.nextLong();
            }
        }
        return Long.MAX_VALUE;
    }

    private String getUploadUrl() {
        String host = configurationProvider.getString(ConfigurationNames.UPLOAD_SERVER_HOST);
        int port = configurationProvider.getInt(ConfigurationNames.UPLOAD_SERVER_PORT);
        return String.format("http://%s:%d/upload", host, port);
    }

    private byte[] createBinaryData() {
        int fileSize = configurationProvider.getInt(ConfigurationNames.PROBE_UPLOAD_SIZE);
        log.info("...Using upload data size: {} bytes.", fileSize);
        byte[] b = new byte[fileSize];
        Arrays.fill(b, (byte) 1);
        return b;
    }

}
