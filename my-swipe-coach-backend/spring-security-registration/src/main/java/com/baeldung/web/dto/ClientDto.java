package com.baeldung.web.dto;

import java.util.Date;

import com.baeldung.persistence.model.Client;
import com.baeldung.persistence.model.ProfileInfo;
import com.baeldung.service.ProfileInfoService;

public class ClientDto {
	
	public Integer id;
	public String token;
	public String firstName;
	public String lastName;
	public Date dateOfBirth;
	public String email;
	public String contactNumber;
	public String userType;
	public long infoId;
	public long getInfoId() {
		return infoId;
	}

	public void setInfoId(long infoId) {
		this.infoId = infoId;
	}

	public ProfileInfo getProfileInfo() {
		return profileInfo;
	}

	public void setProfileInfo(ProfileInfo profileInfo) {
		this.profileInfo = profileInfo;
	}
	public ProfileInfoService profileInfoService;
	public ProfileInfo profileInfo;
	
	
	public ClientDto(Integer id, String token, String firstName, String lastName, Date dateOfBirth, String email,
			String contactNumber, String userType, Long infoId,ProfileInfo profileInfo) {
		super();
		this.id = id;
		this.token = token;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.contactNumber = contactNumber;
		this.userType = userType;
		this.infoId =infoId;
		this.profileInfo = profileInfoService.getProfileInfoByInfoId(infoId).get();
	}
	
	public ClientDto(Client client) {
		this.id = client.getClientAutoId();
		this.firstName = client.getFirstName();
		this.lastName = client.getLastName();
		this.dateOfBirth = client.getDateOfBirth();
		this.email = client.getEmail();
		this.contactNumber = client.getContactNumber();
	}
	public ClientDto() {}
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	

}
