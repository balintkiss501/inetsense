package hu.elte.inetsense.server.collector;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import hu.elte.inetsense.common.dtos.probe.MeasurementDTO;
import hu.elte.inetsense.common.dtos.probe.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.impl.ProbeDataServiceImpl;
import hu.elte.inetsense.server.data.entities.Measurement;
import hu.elte.inetsense.server.data.entities.probe.Probe;
import hu.elte.inetsense.server.data.repository.MeasurementRepository;
import hu.elte.inetsense.server.data.repository.ProbeRepository;

/**
 * @author Zsolt Istvanfi
 */
public class ProbeDataServiceTest {

    @Mock
    private ProbeRepository probeRepository;

    @Mock
    private MeasurementRepository measurementRepository;
    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private ProbeDataServiceImpl probeDataService;

    private Long measurementGlobalId = 0L;

    private ProbeDataDTO probeData;
    private Probe testProbe;

    private List<Measurement> measurementRepositoryList;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        measurementRepositoryList = new ArrayList<>();

        probeData = new ProbeDataDTO();
        probeData.setProbeAuthId("PROBE001");

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

        testProbe = new Probe();
        testProbe.setAuthId(probeData.getProbeAuthId());
        testProbe.setCreatedOn(new Date());

        // Mock probe repository findOneByAuthId
        when(probeRepository.findOneByAuthId(eq("PROBE001")))
                .thenReturn(Optional.of(testProbe));
        when(probeRepository.findOneByAuthId(not(eq("PROBE001"))))
                .thenReturn(Optional.empty());

        // Mock measurement repository save
        doAnswer(invocation -> {
            Measurement measurement = (Measurement)invocation.getArguments()[0];
            measurement.setId(measurementGlobalId++);
            measurementRepositoryList.add(measurement);
            return null;
        }).when(measurementRepository).save(any(Measurement.class));
    }

    @Test(expected = NoSuchElementException.class)
    public void testSaveProbeDataIllegalProbeId() {
        probeData.setProbeAuthId("XXXXXXXX");
        probeDataService.saveProbeData(probeData);
    }
}
