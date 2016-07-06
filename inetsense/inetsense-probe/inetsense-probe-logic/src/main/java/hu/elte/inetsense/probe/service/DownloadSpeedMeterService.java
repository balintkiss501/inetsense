package hu.elte.inetsense.probe.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.common.util.HTTPUtil;
import hu.elte.inetsense.common.util.InetsenseUtil;
import hu.elte.inetsense.probe.service.configuration.ConfigurationNames;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;

public class DownloadSpeedMeterService implements SpeedMeterService {

    private static final Logger log = LogManager.getLogger();
    private ConfigurationProvider configurationProvider;
    private Random rnd = new Random();

    public DownloadSpeedMeterService(ConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
    }

    @Override
    public long measure() throws Exception {
        return download(getDownloadTarget());
    }

    private long download(String downloadTarget) throws ClientProtocolException, IOException {
        log.info("Downloading file: {}....", downloadTarget);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        CloseableHttpResponse response = createConnection(downloadTarget);
        long size = response.getEntity().getContentLength();
        validateSize(downloadTarget, size);

        long downloadSpeed = processData(stopWatch, response);
        log.info("Download complete. ellapes time: {}, speed: {}", stopWatch.toString(), downloadSpeed);
        return downloadSpeed;
    }

    private long processData(StopWatch stopWatch, CloseableHttpResponse response) throws IOException {
        int timeout = configurationProvider.getInt(ConfigurationNames.PROBE_DOWNLOAD_MAX_TIME);
        byte[] b = new byte[1024];
        int count;
        long downloadedSize = 0;
        try(InputStream in = response.getEntity().getContent()) {
            while (timeout > stopWatch.getTime() && (count = in.read(b)) > 0) {
                downloadedSize += count;
            }
        }
        stopWatch.stop();
        long downloadSpeed = InetsenseUtil.calculateSpeed(downloadedSize, stopWatch.getTime());
        return downloadSpeed;
    }

    private void validateSize(String downloadTarget, long size) {
        long minFileSize = configurationProvider.getLong(ConfigurationNames.PROBE_DOWNLOAD_MIN_SIZE);
        if (size < minFileSize) {
            log.warn("Download target: {} size is too small!", downloadTarget);
        }
    }

    private CloseableHttpResponse createConnection(String downloadTarget) throws IOException, ClientProtocolException {
        String url = "";
        if (!downloadTarget.startsWith("http://")) {
            url = "http://";
        }
        url = url + downloadTarget;
        return HTTPUtil.get(url);
    }

    private String getDownloadTarget() {
        String[] targets = configurationProvider.getStringArray(ConfigurationNames.PROBE_TARGET_FILES);
        int index = rnd.nextInt(targets.length);
        return targets[index];
    }
}
