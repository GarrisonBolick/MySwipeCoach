/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.persistence.model;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.baeldung.web.dto.ClientDto;

/**
 *
 * @author kolby
 */
@EntityScan
@Entity
public class ProfileInfo {
     @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
  
    private Integer profileInfoId;
public ProfileInfo(){
    
}
      
    public ProfileInfo(Integer profileInfoId, String profileImage, String profileVideo, String fieldOfExpertise,
			String profileDescription, String meetingPreference, String profileType) {
		super();
		this.profileInfoId = profileInfoId;
		this.profileImage = profileImage;
		this.profileVideo = profileVideo;
		this.fieldOfExpertise = fieldOfExpertise;
		this.profileDescription = profileDescription;
		this.meetingPreference = meetingPreference;
		this.profileType = profileType;
	}
    private String profileImage; 

    private String profileVideo;
   
    private String fieldOfExpertise; 
   
    private String profileDescription;

    private String meetingPreference;
   
    private String profileType;

	public Integer getProfileInfoId() {
		return profileInfoId;
	}

	public void setProfileInfoId(Integer profileInfoId) {
		this.profileInfoId = profileInfoId;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getProfileVideo() {
		return profileVideo;
	}

	public void setProfileVideo(String profileVideo) {
		this.profileVideo = profileVideo;
	}

	public String getFieldOfExpertise() {
		return fieldOfExpertise;
	}

	public void setFieldOfExpertise(String fieldOfExpertise) {
		this.fieldOfExpertise = fieldOfExpertise;
	}

	public String getProfileDescription() {
		return profileDescription;
	}

	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}

	public String getMeetingPreference() {
		return meetingPreference;
	}

	public void setMeetingPreference(String meetingPreference) {
		this.meetingPreference = meetingPreference;
	}

	public String getProfileType() {
		return profileType;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}


	

    







	

   
     
     
    
}
