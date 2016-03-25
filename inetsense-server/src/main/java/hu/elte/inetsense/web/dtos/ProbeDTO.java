package hu.elte.inetsense.web.dtos;

import java.util.List;

/**
 * Created by balintkiss on 3/22/16.
 */
public class ProbeDTO {

    private String probeAuthId;
    private Float lat;
    private Float lon;
    private List<MeasurementDTO> measurements;

    public String getProbeAuthId() {
        return this.probeAuthId;
    }

    public void setProbeAuthId(final String probeAuthId) {
        this.probeAuthId = probeAuthId;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public List<MeasurementDTO> getMeasurements() {
        return this.measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
