package hu.elte.inetsense.common.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by balintkiss on 3/22/16.
 */
public class ProbeDataDTO {

    @NotNull
    @Size(max = 8)
    private String probeAuthId;
    
    @NotNull
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
