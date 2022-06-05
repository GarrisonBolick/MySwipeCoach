/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.service;

import com.baeldung.persistence.dao.CoachRepository;
import com.baeldung.persistence.model.Coach;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class CoachService {
	
	@Autowired	
	private CoachRepository coachRepository;
	
	// Add new Job Types
	public String addCoach(Coach a) {
		
		try {
			coachRepository.save(a);
			return "saved";
		} catch(Exception e) {
			return "failed";
		}
	}


        // Update a Job Type
	public ResponseEntity<Object> updateCoach(Coach a) {
		try {
			Optional<Coach> coachOptional = coachRepository.findById(a.getCoachAutoId());
			if(!coachOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			coachRepository.save(a);
			return ResponseEntity.ok("Updated");
		}catch(Exception e) {
			return ResponseEntity.ok("Failed to update coach record.");
		}
	}


	// Get all Job Types
	public  Iterable<Coach> getAllCoach(){
			
            return coachRepository.findAll();
					
	}
     
    
      
      public String createEmptyCoach(Long userId, String email){
    	  Coach coach = new Coach(null,null,null,null,null,null,email,null,null, userId);
    		try {
    			coachRepository.save(coach);
    			return "saved";
    		} catch(Exception e) {
    			return "failed";
    		}
      }
	
	// Get single Job Type by Id
	public Optional<Coach> getCoach(Integer id) {
		return coachRepository.findById(id);
	}
	
	public Optional<Coach> getCoachByUserId(Long userId) {
		return coachRepository.findByUserId(userId);
	}
	
	public Optional<Coach> getCoachByEmail(String email) {
		return coachRepository.findByEmail(email);
	}


	
	// Delete a Job Type
	public String deleteCoach(Integer id) {
		try{
			coachRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

	
}
