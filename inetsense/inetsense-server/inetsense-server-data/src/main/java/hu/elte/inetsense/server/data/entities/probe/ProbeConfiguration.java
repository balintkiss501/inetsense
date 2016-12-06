package hu.elte.inetsense.server.data.entities.probe;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "probe_configuration")
public class ProbeConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long configurationId;
	private String key;
	private String value;
	private ProbeConfigurationProfile configurationProfile;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configuration_id")
	public Long getConfigurationId() {
		return configurationId;
	}
	
	public void setConfigurationId(Long configurationId) {
		this.configurationId = configurationId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "configuration_profile_id")
	public ProbeConfigurationProfile getConfigurationProfile() {
		return configurationProfile;
	}

	public void setConfigurationProfile(ProbeConfigurationProfile configurationProfile) {
		this.configurationProfile = configurationProfile;
	}
	
}
