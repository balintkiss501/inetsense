package hu.elte.inetsense.server.collector;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.ProbeDataService;
import hu.elte.inetsense.server.data.MeasurementRepository;
import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Measurement;
import hu.elte.inetsense.server.data.entities.Probe;

/**
 * @author Zsolt Istvanfi
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProbeDataServiceTest.TestConfig.class)
public class ProbeDataServiceTest {

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories("hu.elte.inetsense.server.data")
    @EntityScan("hu.elte.inetsense.server.data.entities")
    @ComponentScan("hu.elte.inetsense.server.collector.service")
    public static class TestConfig {
    }

    @Autowired
    private ProbeRepository       probeRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private ProbeDataService      probeDataService;

    private ProbeDataDTO          probeData;

    @Before
    public void before() {
        probeData = new ProbeDataDTO();
        probeData.setProbeAuthId("PROBE001");

        Probe testProbe = new Probe();
        testProbe.setAuthId(probeData.getProbeAuthId());
        testProbe.setCreatedOn(new Date());
        probeRepository.save(testProbe);

        List<MeasurementDTO> measurements = new ArrayList<>(2);

        MeasurementDTO measurement1 = new MeasurementDTO();
        measurement1.setCompletedOn(new Date());
        measurement1.setDownloadSpeed(100L);
        measurement1.setUploadSpeed(10L);
        measurement1.setLat(10.1f);
        measurement1.setLng(20.2f);
        measurements.add(measurement1);

        MeasurementDTO measurement2 = new MeasurementDTO();
        measurement2.setCompletedOn(new Date());
        measurement2.setDownloadSpeed(200L);
        measurement2.setUploadSpeed(20L);
        measurement2.setLat(20.2f);
        measurement2.setLng(40.4f);
        measurements.add(measurement2);

        probeData.setMeasurements(measurements);
    }

    @Test
    public void testSaveProbeData() {
        probeDataService.saveProbeData(probeData);

        Probe testProbe = probeRepository.findOneByAuthId(probeData.getProbeAuthId());
        List<Measurement> measurements = measurementRepository.findAllByProbeOrderByIdAsc(testProbe);

        assertEquals(measurements.size(), 2);

        for (int i = 0; i < probeData.getMeasurements().size(); ++i) {
            Measurement measurement = measurements.get(i);
            MeasurementDTO measurementDTO = probeData.getMeasurements().get(i);

            assertEquals(measurement.getCompletedOn().getTime(), measurementDTO.getCompletedOn().getTime());
            assertEquals(measurement.getDownloadSpeed(), measurementDTO.getDownloadSpeed());
            assertEquals(measurement.getUploadSpeed(), measurementDTO.getUploadSpeed());
            assertEquals(measurement.getLatitude(), measurementDTO.getLat());
            assertEquals(measurement.getLongitude(), measurementDTO.getLng());
        }
    }

}
