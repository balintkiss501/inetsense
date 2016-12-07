package hu.elte.inetsense.server.data.entities.probe;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

import hu.elte.inetsense.server.data.entities.user.User;

/**
 * @author Zsolt Istvanfi
 */
@Entity
public class Probe implements Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	private Date createdOn;
	private String authId;
	private User user;
	private ProbeConfigurationProfile configurationProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "probe_id")
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

    @Column(name = "auth_id", length = 8, unique = true, nullable = false)
    public String getAuthId() {
        return authId;
    }

    public void setAuthId(final String authId) {
        this.authId = authId;
    }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "configuration_profile_id")
    public ProbeConfigurationProfile getConfigurationProfile() {
		return configurationProfile;
	}
	
	public void setConfigurationProfile(ProbeConfigurationProfile configurationProfile) {
		this.configurationProfile = configurationProfile;
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Probe other = (Probe) obj;
        return Objects.equals(this.id, other.id);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
