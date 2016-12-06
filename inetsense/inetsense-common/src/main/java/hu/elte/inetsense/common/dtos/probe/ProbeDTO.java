/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.common.dtos.probe;

import java.util.Date;

import hu.elte.inetsense.common.dtos.user.UserDTO;

/**
 *
 * @author Bekfi Richárd
 */
public class ProbeDTO {

	private Long id;
    private String authId;
    private Date createdOn;
    private UserDTO user;

    public UserDTO getUser() {
		return user;
	}
    
    public void setUser(UserDTO user) {
		this.user = user;
	}
    
    public Long getId() {
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

}
