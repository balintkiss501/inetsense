package hu.elte.inetsense.web.dtos;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zsolt Istvanfi
 */
public class MeasurementDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date              completedOn;
    private Long              downloadSpeed;
    private Long              uploadSpeed;

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
