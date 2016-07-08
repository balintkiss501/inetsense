package hu.elte.inetsense.server.data.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Zsolt Istvanfi
 */
@Entity
public class Measurement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;
    private Date              createdOn;
    private Date              completedOn;
    private Probe             probe;
    private Long              downloadSpeed;
    private Long              uploadSpeed;
    private Double             latitude;
    private Double             Longitude;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(final Date createdOn) {
        this.createdOn = createdOn;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "completed_on", nullable = false)
    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(final Date completedOn) {
        this.completedOn = completedOn;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "probe_id", nullable = false)
    public Probe getProbe() {
        return probe;
    }

    public void setProbe(final Probe probe) {
        this.probe = probe;
    }

    @Column(name = "download_speed", nullable = false)
    public Long getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(Long downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    @Column(name = "upload_speed", nullable = false)
    public Long getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(Long uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double Longitude) {
        this.Longitude = Longitude;
    }

}
