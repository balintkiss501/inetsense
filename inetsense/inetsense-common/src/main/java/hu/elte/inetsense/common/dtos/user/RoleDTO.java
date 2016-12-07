package hu.elte.inetsense.common.dtos.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * @author Zoltan Torok
 */
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    
    @NotNull
	private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
    
    public Long getId() {
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}

}
