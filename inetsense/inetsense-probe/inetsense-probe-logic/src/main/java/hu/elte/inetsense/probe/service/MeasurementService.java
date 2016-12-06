package hu.elte.inetsense.probe.service;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.common.dtos.probe.MeasurementDTO;
import hu.elte.inetsense.common.dtos.probe.ProbeDataDTO;
import hu.elte.inetsense.common.service.configuration.ClockService;
import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.common.service.configuration.ConfigurationProvider;
import hu.elte.inetsense.common.util.HTTPUtil;
import hu.elte.inetsense.common.util.JsonConverter;

/**
 * Coordinating upload/download speed measurement tasks and data upload to the collector server
 * 
 * @author alexb
 *
 */
public class MeasurementService {
    
    private static final Logger log = LogManager.getLogger();

    public class SpeedMeter implements Callable<Long> {
        private SpeedMeterService speedMeterService;

        public SpeedMeter(SpeedMeterService speedMeterService) {
            this.speedMeterService = speedMeterService;
        }

        @Override
        public Long call() throws Exception {
            return speedMeterService.measure();
        }
    }

    private Random rnd = new Random();
    private ProbeDataDTO probeDataDTO;
    private ConfigurationProvider configurationProvider;
    private DownloadSpeedMeterService downloadSpeedMeterService;
    private UploadSpeedMeterService uploadSpeedMeterService;
    private IspService ispService;
    private ClockService clockService;
    private JsonConverter jsonConverter;
    private String probeId;

    public MeasurementService(ConfigurationProvider configurationProvider,
            DownloadSpeedMeterService downloadSpeedMeterService, UploadSpeedMeterService uploadSpeedMeterService,
            ClockService clockService, IspService ispService, JsonConverter jsonConverter) {
        this.configurationProvider = configurationProvider;
        this.downloadSpeedMeterService = downloadSpeedMeterService;
        this.uploadSpeedMeterService = uploadSpeedMeterService;
        this.clockService = clockService;
        this.ispService = ispService;
        this.jsonConverter = jsonConverter;
    }

    /**
     * Performs the measurement of download/upload speed and send it to the collector server.
     * Measurements are collected in case of data sending failure 
     */
    public void measure() {
        if(probeDataDTO == null) {
            createProbeData();
        }
        probeDataDTO.addMeasurement(doMeasure());
        sendMeasurement();
    }

    /**
     * Sends probe data to collector server
     * The data is discarded after successful send.
     */
    private void sendMeasurement() {
        String url = configurationProvider.getCollectorBaseURL() + "/message-endpoint";
        String probaDataJson = jsonConverter.object2Json(probeDataDTO);
        try (CloseableHttpResponse response = HTTPUtil.post(url, probaDataJson )) {
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode == 200) {
                probeDataDTO = null;
                log.info("Measurement sent succesfully");
            } else {
                log.warn("Something went wrong while sendin Measurement. Server responded with an error: {}", statusCode);
                processError(response);
            }
        } catch (Exception e) {
            log.error("While sending Measurement to server", e);
        }
    }

    private void processError(CloseableHttpResponse response) throws IOException {
        try (Scanner sc = new Scanner(response.getEntity().getContent())) {
            while(sc.hasNextLine()) {
                log.warn(sc.nextLine());
            }
        }
    }

    private MeasurementDTO doMeasure() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        String downloadTarget = getNextDownloadTarget();
		downloadSpeedMeterService.setDownloadTarget(downloadTarget);
        Future<Long> downloadFuture = executor.submit(new SpeedMeter(downloadSpeedMeterService));
        Future<Long> uploadFuture = executor.submit(new SpeedMeter(uploadSpeedMeterService));
        executor.shutdown();
        try {
            executor.awaitTermination(1L, TimeUnit.MINUTES);
            long downloadSpeed = downloadFuture.get();
            long uploadSpeed = uploadFuture.get();
            return createMeasurement(downloadSpeed, uploadSpeed, downloadTarget);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }
    
    private String getNextDownloadTarget() {
        String[] targets = configurationProvider.getStringArray(ConfigurationNames.PROBE_TARGET_FILES);
        int index = rnd.nextInt(targets.length);
        return targets[index];
    }

    private MeasurementDTO createMeasurement(long downloadSpeed, long uploadSpeed, String downloadTarget) {
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setDownloadSpeed(downloadSpeed);
        measurement.setUploadSpeed(uploadSpeed);
        measurement.setDownloadTarget(downloadTarget);
        measurement.setCompletedOn(clockService.getCurrentTime());
        measurement.setIsp(ispService.getIspName());
        return measurement;
    }

    private void createProbeData() {
        probeDataDTO = new ProbeDataDTO();
        probeDataDTO.setProbeAuthId(getProbeId());
    }
    
    public String getProbeId() {
        if(probeId == null) {
            probeId = configurationProvider.getString(ConfigurationNames.PROBE_ID);
        }
        return probeId;
    }
    
}
