package hu.elte.inetsense.probe.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.common.service.configuration.BaseConfigurationProvider;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.common.util.HTTPUtil;
import hu.elte.inetsense.common.util.InetsenseUtil;

public class DownloadSpeedMeterService implements SpeedMeterService {

    private static final Logger log = LogManager.getLogger();
    private int threadCount;
    private BaseConfigurationProvider configurationProvider;
    private String downloadTarget;

    public DownloadSpeedMeterService(BaseConfigurationProvider configurationProvider) {
        this.configurationProvider = configurationProvider;
        threadCount = configurationProvider.getInt(ConfigurationNames.PROBE_DOWNLOAD_THREAD_COUNT);
    }

    @Override
    public long measure() throws Exception {
    	log.info("Measuring download speed:");
        long downloadSpeed = 0;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<Long>> downloadFutures = scheduleDownloadTasks(executor);
        executor.shutdown();
        try{
          executor.awaitTermination(1L, TimeUnit.MINUTES);
          downloadSpeed = getDownloadSpeed(downloadFutures);

        }catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }

        return downloadSpeed;
    }
    
    private Long getDownloadSpeed(List<Future<Long>> downloadFutures) throws Exception{
        long downloadSpeed = (long) downloadFutures.stream().mapToLong(f -> {
			try { return f.get(); } 
			catch (Exception e) {}
			return 0;
		}).average().orElse(0);
        log.info("Download complete. Download speed: {}....", downloadSpeed);
        return downloadSpeed;
    }
    
    private List<Future<Long>> scheduleDownloadTasks(ExecutorService executor){
        List<Future<Long>> downloadFutures = new ArrayList<Future<Long>>();
        for(int i = 0; i < threadCount; ++i){
              downloadFutures.add(executor.submit(new DownloadMeter(getDownloadTarget(),configurationProvider)));
        }
        return downloadFutures;
    }

    public String getDownloadTarget() {
		return downloadTarget;
	}
    
    public void setDownloadTarget(String downloadTarget) {
		this.downloadTarget = downloadTarget;
	}
    
    private class DownloadMeter implements Callable<Long>{

        private String downloadTarget;
        private BaseConfigurationProvider configurationProvider;


        DownloadMeter(String downloadTarget, BaseConfigurationProvider configurationProvider){
          this.downloadTarget = downloadTarget;
          this.configurationProvider = configurationProvider;
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

          @Override
          public Long call() throws Exception {
              return download(this.downloadTarget);
          }
      }
}
