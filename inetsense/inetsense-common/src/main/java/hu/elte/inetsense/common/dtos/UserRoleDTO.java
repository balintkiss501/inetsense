package hu.elte.inetsense.common.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Zoltan Torok
 */
public class UserRoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@NotNull
	private Long userRoleId;

	@NotNull
	private Long userId;

	@NotEmpty
	private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }


}
