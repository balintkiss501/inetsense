package hu.elte.inetsense.common.dtos;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Zsolt Istvanfi
 */
public class MeasurementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(timezone = "UTC")
    private Date              completedOn;

    private Float             lat;
    private Float             lng;
    private Long              downloadSpeed;
    private Long              uploadSpeed;

    public Float getLat() {
        return lat;
    }

    public void setLat(final Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(final Float lng) {
        this.lng = lng;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(final Date completedOn) {
        this.completedOn = completedOn;
    }

    public Long getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(final Long downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public Long getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(final Long uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

}
