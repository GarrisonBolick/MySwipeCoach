package com.baeldung.web.dto;

import java.util.Date;

import com.baeldung.persistence.model.Client;
import com.baeldung.persistence.model.Coach;

public class CoachDto {
	
	public Integer id;
	public String token;
	public String firstName;
	public String lastName;
	public Date dateOfBirth;
	public String email;
	public String contactNumber;
	public String userType;
	
	public CoachDto(Integer id, String token, String firstName, String lastName, Date dateOfBirth, String email,
			String contactNumber, String userType) {
		super();
		this.id = id;
		this.token = token;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.contactNumber = contactNumber;
		this.userType = userType;
	}
	
	public CoachDto(Coach coach) {
		this.id = coach.getCoachAutoId();
		this.firstName = coach.getFirstName();
		this.lastName = coach.getLastName();
		this.dateOfBirth = coach.getDateOfBirth();
		this.email = coach.getEmail();
		this.contactNumber = coach.getContactNumber();
	}
	public CoachDto() {}
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
