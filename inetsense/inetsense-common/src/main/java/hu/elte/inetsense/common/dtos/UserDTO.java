package hu.elte.inetsense.common.dtos;

import java.io.Serializable;

/**
 * @author Zsolt Istvanfi
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            email;
    private String            password;

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

}
