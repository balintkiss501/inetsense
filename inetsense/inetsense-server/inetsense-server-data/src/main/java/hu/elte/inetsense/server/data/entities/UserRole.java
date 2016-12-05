package hu.elte.inetsense.server.data.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Zoltan Daniel Torok
 */
@Entity
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long              id;
    private Long              userId;
    private Long              roleId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    @Column(name = "role_id")
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        return (userId == 0 || roleId == 0)
          ? 0
          : (userId.hashCode() + roleId.hashCode());
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
        return Objects.equals(this.userId, other.userId)
               && Objects.equals(this.roleId, other.roleId);
    }
}
