package hu.elte.inetsense.common.dtos;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Zsolt Istvanfi
 */
public class MeasurementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(timezone = "UTC")
    @Past
    private Date              completedOn;

    @Range(min = -90, max = 90)
    private Double             lat;

    @Range(min = -180, max = 180)
    private Double             lng;
    
    @Min(0)
    private Long              downloadSpeed;

    @Min(0)
    private Long              uploadSpeed;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
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

    public void setDownloadSpeed(Long downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public Long getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(Long uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

}
