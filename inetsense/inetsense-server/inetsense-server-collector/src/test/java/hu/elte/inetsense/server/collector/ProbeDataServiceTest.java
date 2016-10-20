package hu.elte.inetsense.server.collector;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.impl.ProbeDataServiceImpl;
import hu.elte.inetsense.server.data.MeasurementRepository;
import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Measurement;
import hu.elte.inetsense.server.data.entities.Probe;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

/**
 * @author Zsolt Istvanfi
 */
public class ProbeDataServiceTest {

    @Mock
    private ProbeRepository probeRepository;

    @Mock
    private MeasurementRepository measurementRepository;

    @InjectMocks
    private ProbeDataServiceImpl probeDataService;

    private Long measurementGlobalId = 0L;

    private ProbeDataDTO probeData;

    private List<Probe> probeRepositoryList;
    private List<Measurement> measurementRepositoryList;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        probeRepositoryList = new ArrayList<>();
        measurementRepositoryList = new ArrayList<>();

        // Mock probe repository save
        doAnswer(invocation -> {
                probeRepositoryList.add((Probe)invocation.getArguments()[0]);
                return null;
        }).when(probeRepository).save(any(Probe.class));

        // Mock probe repository findOneByAuthId
        doAnswer(invocation -> {
                String authId = (String)invocation.getArguments()[0];

                for (Probe p : probeRepositoryList) {
                    if (authId.equals(p.getAuthId())) {
                        return Optional.of(p);
                    }
                }
                return Optional.empty();
        }).when(probeRepository).findOneByAuthId(anyString());

        // Mock measurement repository save
        doAnswer(invocation -> {
                Measurement measurement = (Measurement)invocation.getArguments()[0];
                measurement.setId(measurementGlobalId++);
                measurementRepositoryList.add(measurement);
                return null;
        }).when(measurementRepository).save(any(Measurement.class));

        // Mock measurement repository findAllByProbeOrderByIdAsc
        doAnswer(invocation -> {
                Probe currentProbe = (Probe)invocation.getArguments()[0];
                List<Measurement> measurements = new ArrayList<>();
                for (Measurement m : measurementRepositoryList) {
                    if (m.getProbe().equals(currentProbe)) {
                        measurements.add(m);
                    }
                }

                measurements.sort((m1, m2) -> m1.getId().compareTo(m2.getId()));
                return measurements;
        }).when(measurementRepository).findAllByProbeOrderByIdAsc(any(Probe.class));

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
        measurement1.setLat(10.1d);
        measurement1.setLng(20.2d);
        measurements.add(measurement1);

        MeasurementDTO measurement2 = new MeasurementDTO();
        measurement2.setCompletedOn(new Date());
        measurement2.setDownloadSpeed(200L);
        measurement2.setUploadSpeed(20L);
        measurement2.setLat(20.2d);
        measurement2.setLng(40.4d);
        measurements.add(measurement2);

        probeData.setMeasurements(measurements);
    }

    @Test
    public void testSaveProbeData() {
        probeDataService.saveProbeData(probeData);

        Probe testProbe = probeRepository.findOneByAuthId(probeData.getProbeAuthId()).get();
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
