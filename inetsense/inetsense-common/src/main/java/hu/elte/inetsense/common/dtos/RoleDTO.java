package hu.elte.inetsense.common.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author Zoltan Torok
 */
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String            name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
