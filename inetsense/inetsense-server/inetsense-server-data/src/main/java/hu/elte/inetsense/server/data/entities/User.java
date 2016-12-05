package hu.elte.inetsense.server.data.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Zsolt Istvanfi
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Date createdOn;

	private String email;
	private String password;
	private List<Role> roles;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
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

	@Override
	public int hashCode() {
		return id == 0 ? 0 : id.hashCode();
	}

	@Column(length = 254, unique = true, nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Column(length = 60, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	
	@JoinTable(
		name = "user_role",
		joinColumns=@JoinColumn(name="user_id", referencedColumnName="user_id"),
		inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName="role_id")
	)
	@ManyToMany()
	public List<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
		final User other = (User) obj;
		return Objects.equals(this.id, other.id);
	}
}
