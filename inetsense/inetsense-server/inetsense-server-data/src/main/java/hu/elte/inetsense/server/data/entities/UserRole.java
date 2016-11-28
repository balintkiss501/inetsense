package hu.elte.inetsense.server.data.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Zoltan Daniel Torok
 */
@Entity
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              user_id;
    private Long              role_id;

    @Id
    @Column(name = "user_id")
    public Long getUserId() {
        return user_id;
    }

    public void setUserId(final Long user_id) {
        this.user_id = user_id;
    }

    @Id
    @Column(name = "role_id")
    public Long getRoleId() {
        return role_id;
    }

    public void setRoleId(final Long role_id) {
        this.role_id = role_id;
    }

    @Override
    public int hashCode() {
        return (user_id == 0 || role_id == 0)
          ? 0
          : (user_id.hashCode() + role_id.hashCode());
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
        final UserRole other = (UserRole) obj;
        return Objects.equals(this.user_id, other.user_id)
               && Objects.equals(this.role_id, other.role_id);
    }
}
