package hu.elte.inetsense.server.data.entities.probe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "probe_configuration_profile")
public class ProbeConfigurationProfile implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long profileId;
	private String name;
	private boolean enabled;
	private boolean singleMeasurement;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "single_measurement")
	public boolean isSingleMeasurement() {
		return singleMeasurement;
	}

	public void setSingleMeasurement(boolean singleMeasurement) {
		this.singleMeasurement = singleMeasurement;
	}
	
	
}
