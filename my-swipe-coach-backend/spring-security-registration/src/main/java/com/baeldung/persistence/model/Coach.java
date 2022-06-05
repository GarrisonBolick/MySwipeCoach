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

import com.baeldung.web.dto.CoachDto;

/**
 *
 * @author kolby
 */
@EntityScan
@Entity
public class Coach {
     @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
  
    private Integer coachAutoId;
public Coach(){
    
}
  

     
   

	public Coach(CoachDto coachDto) {
		
		this.firstName = coachDto.getFirstName();
		this.lastName = coachDto.getLastName();
		this.dateOfBirth = coachDto.getDateOfBirth();
		this.email = coachDto.getEmail();
		this.contactNumber = coachDto.getContactNumber();
		
	}
	
    public Coach(Integer coachAutoId, Integer quizAnswerId, Integer infoId, String firstName, String lastName, Date dateOfBirth,
		String email, String contactNumber, String paidFlag,Long userId) {
	
	this.coachAutoId = coachAutoId;
	this.quizAnswerId = quizAnswerId;
	this.infoId = infoId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.dateOfBirth = dateOfBirth;
	this.email = email;
	this.contactNumber = contactNumber;
	this.paidFlag = paidFlag;
	this.userId = userId;
}
    
    public void update(CoachDto coachDto) {
		
		this.firstName = coachDto.getFirstName();
		this.lastName = coachDto.getLastName();
		this.dateOfBirth = coachDto.getDateOfBirth();
		this.email = coachDto.getEmail();
		this.contactNumber = coachDto.getContactNumber();
    }
    
  
    private Integer quizAnswerId; 

    private Integer infoId;
   
    private String firstName; 
   
    private String lastName;

    private Date dateOfBirth;
   
    private String email;

    private String contactNumber;
 
    private String paidFlag;
   
    private Long userId;
	public Integer getCoachAutoId() {
		return coachAutoId;
	}





	public void setCoachAutoId(Integer coachAutoId) {
		this.coachAutoId = coachAutoId;
	}





	public Integer getQuizAnswerId() {
		return quizAnswerId;
	}





	public void setQuizAnswerId(Integer quizAnswerId) {
		this.quizAnswerId = quizAnswerId;
	}





	public Integer getInfoId() {
		return infoId;
	}





	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
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





	public String getPaidFlag() {
		return paidFlag;
	}





	public void setPaidFlag(String paidFlag) {
		this.paidFlag = paidFlag;
	}





	public Long getUserId() {
		return userId;
	}





	public void setUserId(Long userId) {
		this.userId = userId;
	}






	

   
     
     
    
}
