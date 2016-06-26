package hu.elte.inetsense.server.collector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.ProbeDataValidator;
import hu.elte.inetsense.server.collector.service.impl.ProbeDataValidatorImpl;
import hu.elte.inetsense.server.collector.util.JsonValidationException;

public class ProbeDataValidatorTest {

    private ProbeDataValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new ProbeDataValidatorImpl();
    }

    @Test
    public void testMissingAuthId() {
        ProbeDataDTO probeData = createProbeData("", createMeasurement(10L, 10L, new Date()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
        probeData = createProbeData("   ", createMeasurement(10L, 10L, new Date()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
    }

    @Test
    public void testNullAuthId() {
        ProbeDataDTO probeData = createProbeData(null, createMeasurement(10L, 10L, new Date()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
    }

    @Test
    public void testLongAuthId() {
        ProbeDataDTO probeData = createProbeData("123456789", createMeasurement(10L, 10L, new Date()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
    }

    @Test
    public void testInvalidSpeed() {
        ProbeDataDTO probeData = createProbeData("12345678", createMeasurement(-10L, 10L, new Date()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
        probeData = createProbeData("12345678", createMeasurement(10L, -10L, new Date()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
        probeData = createProbeData("12345678", createMeasurement(-10L, -10L, new Date()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(2, e.getErrors().size());
        }
    }

    @Test
    public void testInvalidCompletedDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 5);
        ProbeDataDTO probeData = createProbeData("1234567", createMeasurement(10L, 10L, c.getTime()));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
    }

    @Test
    public void testOneCoordinateMissing() {
        ProbeDataDTO probeData = createProbeData("1234567", createMeasurement(10L, 10L, new Date(), null, 10.0F));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
        probeData = createProbeData("1234567", createMeasurement(10L, 10L, new Date(), 10.0F, null));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
        probeData = createProbeData("1234567", createMeasurement(10L, 10L, new Date(), 10.0F, 10.0F));
        try {
            validator.validate(probeData);
        } catch (JsonValidationException e) {
            fail();
        }
    }
    
    @Test
    public void testInvalidLat() {
        ProbeDataDTO probeData = createProbeData("1234567", createMeasurement(10L, 10L, new Date(), -91F, 10.0F));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
        probeData = createProbeData("1234567", createMeasurement(10L, 10L, new Date(), 91.0F, 15F));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
    }

    @Test
    public void testInvalidLng() {
        ProbeDataDTO probeData = createProbeData("1234567", createMeasurement(10L, 10L, new Date(), 10F, -181.0F));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
        probeData = createProbeData("1234567", createMeasurement(10L, 10L, new Date(), 15.0F, 181F));
        try {
            validator.validate(probeData);
            fail();
        } catch (JsonValidationException e) {
            assertEquals(1, e.getErrors().size());
        }
    }


    private ProbeDataDTO createProbeData(String probeAuthId, MeasurementDTO... measurements) {
        ProbeDataDTO result = new ProbeDataDTO();
        result.setProbeAuthId(probeAuthId);
        result.setMeasurements(Arrays.asList(measurements));
        return result;
    }

    private MeasurementDTO createMeasurement(Long downloadSpeed, Long uploadSpeed, Date completedOn, Float lat, Float lng) {
        MeasurementDTO result = createMeasurement(downloadSpeed, uploadSpeed, completedOn);
        result.setLat(lat);
        result.setLng(lng);
        return result;
    }

    private MeasurementDTO createMeasurement(Long downloadSpeed, Long uploadSpeed, Date completedOn) {
        MeasurementDTO result = new MeasurementDTO();
        result.setDownloadSpeed(downloadSpeed);
        result.setUploadSpeed(uploadSpeed);
        result.setCompletedOn(completedOn);
        return result;
    }

}
