package hu.elte.inetsense.web.dtos;

import java.util.List;

/**
 * Created by balintkiss on 3/22/16.
 */
public class ProbeDataDTO {

    private String probeAuthId;
    private List<MeasurementDTO> measurements;

    public String getProbeAuthId() {
        return this.probeAuthId;
    }

    public void setProbeAuthId(final String probeAuthId) {
        this.probeAuthId = probeAuthId;
    }

    public List<MeasurementDTO> getMeasurements() {
        return this.measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
