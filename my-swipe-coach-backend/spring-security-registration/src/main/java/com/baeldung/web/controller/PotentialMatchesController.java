/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.persistence.model.Client;
import com.baeldung.persistence.model.PotentialMatch;
import com.baeldung.service.ClientService;
import com.baeldung.service.PotentialMatchService;
import com.baeldung.web.dto.ClientDto;

@Controller
@RequestMapping(path="/potentialMatch")
@CrossOrigin
public class PotentialMatchesController {
	
	@Autowired
	private PotentialMatchService potentialMatchService;
	@Autowired
	private ClientService clientService;

	// Add new job type
	@PostMapping(path="/add/{id}")
	public @ResponseBody String updateNewPotentialMatch (@RequestBody PotentialMatch pm) {
		//PotentialMatch m = new PotentialMatch();
		
		return potentialMatchService.updatePotentialMatch(pm);
	}
	
	// Get all job types
	@GetMapping(path="/CoachMatches")
	public @ResponseBody ResponseEntity<Iterable<Integer>> getAllPotentialCoachMatch(Integer id) {
            
            Iterable<Integer> allAList;
            allAList = potentialMatchService.getAllPotentialCoachMatch(id);
		return new ResponseEntity<>(allAList,
                HttpStatus.OK);
	}
	// Get all job types
		@GetMapping(path="/ClientMatches")
		public @ResponseBody ResponseEntity<Iterable<ClientDto>> getAllPotentialClientMatch(@RequestParam String email) {
	            Optional<Client> client = clientService.getClientByEmail(email);
	            Iterable<ClientDto> allAList;
	            allAList = potentialMatchService.getAllPotentialClientMatch(client.get().getClientAutoId());
			return new ResponseEntity<>(allAList,
	                HttpStatus.OK);
		}
     
	// Get single job type by Id
	@GetMapping(path="/{id}")
	public @ResponseBody ResponseEntity<Optional<PotentialMatch>> getPotentialMatchById(@PathVariable(name = "id") Integer id) {
		return new ResponseEntity<>(potentialMatchService.getPotentialMatch(id),HttpStatus.OK);
	}
	
//	// Update a Job Type
//	@PostMapping(path="/update")
//	public @ResponseBody ResponseEntity<Object> updatePotentialMatch(@RequestBody 
//        PotentialMatch a) {
//		return potentialMatchService.updatePotentialMatch(a);
//	}
//	
	
	

	// Delete a PotentialMatch
	@RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String deletePotentialMatch(@PathVariable(name = "id") Integer id) {
		return potentialMatchService.deletePotentialMatch(id);
	}
}
