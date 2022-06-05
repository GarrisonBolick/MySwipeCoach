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

/**
 *
 * @author kolby
 */
@EntityScan
@Entity
public class PotentialMatch {
     @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
     @Column(name="PotentialMatchesAutoId")
    private Integer PotentialMatchesAutoId;
public PotentialMatch(){
    
}
  

     
   


	
    public PotentialMatch(Integer clientId, Integer coachId, Integer clientSwiped,Integer coachSwiped) {
	
	this.ClientId = clientId;
	this.CoachId = coachId;
	this.ClientSwiped = clientSwiped;
	this.CoachSwiped = coachSwiped;

}
    
    @Column(name="ClientId")
    private Integer ClientId; 
    @Column(name="CoachId")
    private Integer CoachId;
    @Column(name="clientSwiped")
    private Integer ClientSwiped; 
    @Column(name="coachSwiped")
    private Integer CoachSwiped;
	public Integer getPotentialMatchesAutoId() {
		return PotentialMatchesAutoId;
	}







	public void setPotentialMatchesAutoId(Integer potentialMatchesAutoId) {
		PotentialMatchesAutoId = potentialMatchesAutoId;
	}







	public Integer getClientId() {
		return ClientId;
	}







	public void setClientId(Integer clientId) {
		ClientId = clientId;
	}







	public Integer getCoachId() {
		return CoachId;
	}







	public void setCoachId(Integer coachId) {
		CoachId = coachId;
	}







	public Integer getClientSwiped() {
		return ClientSwiped;
	}







	public void setClientSwiped(Integer clientSwiped) {
		ClientSwiped = clientSwiped;
	}







	public Integer getCoachSwiped() {
		return CoachSwiped;
	}







	public void setCoachSwiped(Integer coachSwiped) {
		CoachSwiped = coachSwiped;
	} 
 











	

   
     
     
    
}
