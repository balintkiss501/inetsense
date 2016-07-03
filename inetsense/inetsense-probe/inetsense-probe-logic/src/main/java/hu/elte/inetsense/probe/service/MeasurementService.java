package hu.elte.inetsense.probe.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.probe.service.configuration.ClockService;
import hu.elte.inetsense.probe.service.configuration.ConfigurationNames;
import hu.elte.inetsense.probe.service.configuration.ConfigurationProvider;

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

    private ProbeDataDTO probeDataDTO;
    private ConfigurationProvider configurationProvider;
    private DownloadSpeedMeterService downloadSpeedMeterService;
    private ClockService clockService;
    private String probeId;

    public MeasurementService(ConfigurationProvider configurationProvider,
            DownloadSpeedMeterService downloadSpeedMeterService, ClockService clockService) {
        this.configurationProvider = configurationProvider;
        this.downloadSpeedMeterService = downloadSpeedMeterService;
        this.clockService = clockService;
        probeId = this.configurationProvider.getString(ConfigurationNames.PROBE_ID);
    }

    public void measure() {
        if(probeDataDTO == null) {
            createProbeData();
        }
        probeDataDTO.addMeasurement(doMeasure());
    }

    private MeasurementDTO doMeasure() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Long> downloadFuture = executor.submit(new SpeedMeter(downloadSpeedMeterService));
        executor.shutdown();
        try {
            executor.awaitTermination(1L, TimeUnit.MINUTES);
            long downloadSpeed = downloadFuture.get();
            long uploadSpeed = 1;
            return createMeasurement(downloadSpeed, uploadSpeed);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    private MeasurementDTO createMeasurement(long downloadSpeed, long uploadSpeed) {
        MeasurementDTO measurement = new MeasurementDTO();
        measurement.setDownloadSpeed(downloadSpeed);
        measurement.setUploadSpeed(uploadSpeed);
        measurement.setCompletedOn(clockService.getCurrentTime());
        return measurement;
    }

    private void createProbeData() {
        probeDataDTO = new ProbeDataDTO();
        probeDataDTO.setProbeAuthId(probeId);
    }
}
