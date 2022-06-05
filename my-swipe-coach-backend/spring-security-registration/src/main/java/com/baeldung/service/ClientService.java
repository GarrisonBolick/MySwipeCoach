/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.service;

import com.baeldung.persistence.dao.ClientRepository;
import com.baeldung.persistence.model.Client;

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
public class ClientService {
	
	@Autowired	
	private ClientRepository clientRepository;
	
	// Add new Job Types
	public String addClient(Client a) {
		
		try {
			clientRepository.save(a);
			return "saved";
		} catch(Exception e) {
			return "failed";
		}
	}


        // Update a Job Type
	public ResponseEntity<Object> updateClient(Client a) {
		try {
			Optional<Client> clientOptional = clientRepository.findById(a.getClientAutoId());
			if(!clientOptional.isPresent()) {
				return ResponseEntity.notFound().build();
			}
			clientRepository.save(a);
			return ResponseEntity.ok("Updated");
		}catch(Exception e) {
			return ResponseEntity.ok("Failed to update client record.");
		}
	}


	// Get all Job Types
	public  Iterable<Client> getAllClient(){
			
            return clientRepository.findAll();
					
	}
     
    
      
      public String createEmptyClient(Long userId, String email){
    	  Client client = new Client(null,null,null,null,null,null,email,null,null, userId);
    		try {
    			clientRepository.save(client);
    			return "saved";
    		} catch(Exception e) {
    			return "failed";
    		}
      }
	
	// Get single Job Type by Id
	public Optional<Client> getClient(Integer id) {
		return clientRepository.findById(id);
	}
	
	public Optional<Client> getClientByUserId(Long userId) {
		return clientRepository.findByUserId(userId);
	}
	
	public Optional<Client> getClientByEmail(String email) {
		return clientRepository.findByEmail(email);
	}


	
	// Delete a Job Type
	public String deleteClient(Integer id) {
		try{
			clientRepository.deleteById(id);
			return "Deleted";
		}catch(Exception e) {
			return "Failed";
		}
	}

	
}
