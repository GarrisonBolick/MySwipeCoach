/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.service;

import com.baeldung.persistence.dao.ProfileInfoRepository;
import com.baeldung.persistence.model.ProfileInfo;

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
public class ProfileInfoService {
	
	@Autowired	
	private ProfileInfoRepository profileInfoRepository;
	
	// Add new Job Types
	public String addProfileInfo(ProfileInfo a) {
		
		try {
			profileInfoRepository.save(a);
			return "saved";
		} catch(Exception e) {
			return "failed";
		}
	}


        // Update a Job Type
	public ResponseEntity<Object> updateProfileInfo(ProfileInfo a) {
		try {
			Optional<ProfileInfo> profileInfoOptional = profileInfoRepository.findById(a.getProfileInfoId());
			if(!profileInfoOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			profileInfoRepository.save(a);
			return ResponseEntity.ok("Updated");
		}catch(Exception e) {
			return ResponseEntity.ok("Failed to update profileInfo record.");
		}
	}


	 
	public  Iterable<ProfileInfo> getAllProfileInfo(){
			
            return profileInfoRepository.findAll();
					
	}
     
    
      
    	
	 
	public Optional<ProfileInfo> getProfileInfo(Integer id) {
		return profileInfoRepository.findById(id);
	}
	
	 
		public Optional<ProfileInfo> getProfileInfoByInfoId(Long id) {
			return profileInfoRepository.findByUserId(id);
		}
	


	
	// Delete a Job Type
	public String deleteProfileInfo(Integer id) {
		try{
			profileInfoRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

	
}
