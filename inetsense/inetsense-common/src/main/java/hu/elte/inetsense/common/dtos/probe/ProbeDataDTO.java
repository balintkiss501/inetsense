package hu.elte.inetsense.common.dtos.probe;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

/**
 * Created by balintkiss on 3/22/16.
 */
public class ProbeDataDTO {

    @NotNull(message = "Probe auth id cannot be null!")
    @Length(min = 1, max = 8, message = "Probe auth id length must be between 1 and 8!")
    private String probeAuthId;
    
    @NotNull(message = "Measurements list cannot be null!")
    @Size(min = 1, message = "Measurement must contain at 1 measurement!")
    @Valid
    private List<MeasurementDTO> measurements = new ArrayList<>();

    public String getProbeAuthId() {
        return probeAuthId;
    }

    public void setProbeAuthId(String probeAuthId) {
        this.probeAuthId = probeAuthId;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public void addMeasurement(MeasurementDTO measurement) {
        measurements.add(measurement);
    }
}
