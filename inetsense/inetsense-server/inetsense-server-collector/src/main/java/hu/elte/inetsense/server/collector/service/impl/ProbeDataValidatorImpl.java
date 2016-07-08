package hu.elte.inetsense.server.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.service.ClockService;
import hu.elte.inetsense.server.collector.service.ProbeDataValidator;
import hu.elte.inetsense.server.collector.util.JsonValidationException;

/**
 * Service for the Standardized Interface: validation of incoming JSON data against existing JSON schema.
 * <p>
 * Created by balintkiss on 3/22/16.
 */
@Component
public class ProbeDataValidatorImpl implements ProbeDataValidator {

	private static final int MAX_AUTH_ID_LENGTH = 8;
    private static final int LATITUDE_LIMIT = 90;
	private static final int LONGITUDE_LIMIT = 180;

    @Autowired
    private ClockService clockService;
    
    /* (non-Javadoc)
     * @see hu.elte.inetsense.server.collector.service.ProbeDataValidator#validate(hu.elte.inetsense.common.dtos.ProbeDataDTO)
     */
    @Override
    public void validate(ProbeDataDTO probeDataDTO) throws JsonValidationException {
        List<String> errors = new ArrayList<>();
        
        validateAuthId(probeDataDTO.getProbeAuthId(), errors);
        validateMeasurements(probeDataDTO.getMeasurements(), errors);
        
        if(!errors.isEmpty()) {
        	throw new JsonValidationException(errors);
        }
    }

	private void validateAuthId(String probeAuthId, List<String> errors) {
		if(StringUtils.isBlank(probeAuthId) || probeAuthId.length() > MAX_AUTH_ID_LENGTH) {
			errors.add("Invalud Probe Auth ID: '" + probeAuthId + "'");
		}
	}

	private void validateMeasurements(List<MeasurementDTO> measurements, List<String> errors) {
		if(measurements == null || measurements.isEmpty()) {
			errors.add("Received probe data must contain at least one meausrement!");
			return;
		}
		measurements.forEach(mdto -> validateMeasurement(mdto, errors));
	}

	private void validateMeasurement(MeasurementDTO measurement, List<String> errors) {
		validateCoordinates(measurement.getLat(), measurement.getLng(), errors);
		validateMeasuredSpeed(measurement.getDownloadSpeed(), "DOWNLOAD", errors);
		validateMeasuredSpeed(measurement.getUploadSpeed(), "UPLOAD", errors);
		validateCompletedDate(measurement.getCompletedOn(), errors);
	}

	private void validateCompletedDate(Date completedOn, List<String> errors) {
	    if(clockService.getCurrentTime().before(completedOn)) {
	        errors.add("Completed on date cannot be in the future. completed on Date: " + completedOn);
	    }
	}

	private void validateMeasuredSpeed(Long speed, String type, List<String> errors) {
		if(speed == null) {
			errors.add("Measured value for '" + type + "' is missing!");
		}
		if(speed < 0) {
			errors.add("Measured value for '" + type + "' = " + speed + " is incorrect!");
		}
	}

	private void validateCoordinates(Float lat, Float lng, List<String> errors) {
		if(lat != null && lng != null) {
			if(lat > LATITUDE_LIMIT || lat < -LATITUDE_LIMIT) {
				errors.add("Incorrect value of latitude: " + lat);
			}
			if(lng > LONGITUDE_LIMIT || lng < -LONGITUDE_LIMIT) {
				errors.add("Incorrect value of Longitude: " + lng);
			}
		} else if(lat != null || lng != null) {
			errors.add("One of the coordinates is missing from this measurement!");
		}
	}
	
	public void setClockService(ClockService clockService) {
        this.clockService = clockService;
    }
}
