package hu.elte.inetsense.common.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Zsolt Istvanfi
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Email
    private String            email;

    @NotEmpty
    private String            password;

    private String            roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRoles() {
      return roles;
    }

    public void setRoles(final String roles) {
      this.roles = roles;
    }
}
