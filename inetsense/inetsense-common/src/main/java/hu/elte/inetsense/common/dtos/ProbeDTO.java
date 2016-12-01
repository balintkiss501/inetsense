/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.common.dtos;

import java.util.Date;

/**
 *
 * @author Bekfi Richárd
 */
public class ProbeDTO {

    private String authId;
    private Date createdOn;
    private String userEmail;

    public ProbeDTO() {
    }
    
    public ProbeDTO(String authId, Date createdOn) {
        this.authId = authId;
        this.createdOn = createdOn;
    }

    public ProbeDTO(String authId, Date createdOn, String userEmail) {
        this.authId = authId;
        this.createdOn = createdOn;
        this.userEmail = userEmail;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

}
