package hu.elte.inetsense.web.dtos;

import hu.elte.inetsense.domain.entities.Measurement;

import java.util.List;

/**
 * Created by balintkiss on 3/22/16.
 */
public class JsonMessageObject {

    private Long id;
    private Float lat;
    private Float ion;
    private List<MeasurementDTO> measurements;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getIon() {
        return ion;
    }

    public void setIon(Float ion) {
        this.ion = ion;
    }

    public List<MeasurementDTO> getMeasurements() {
        return this.measurements;
    }

    public void setMeasurements(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
