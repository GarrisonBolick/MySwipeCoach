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
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.persistence.model.Coach;
import com.baeldung.persistence.model.User;
import com.baeldung.service.CoachService;
import com.baeldung.service.PotentialMatchService;
import com.baeldung.service.UserService;
import com.baeldung.web.dto.CoachDto;

@RestController
@RequestMapping(path="/coach")
@CrossOrigin
public class CoachController {
	
	@Autowired
	private CoachService coachService;
	@Autowired
	private PotentialMatchService potentialMatchService;
	
	@Autowired
	private UserService userService;

	// Add new job type
	@PostMapping(path="/add")
	public @ResponseBody String addNewCoach (@RequestBody Coach a) {
		//Coach c = new Coach();
		//c.setId(3);
		//c.setFirstName("test");
		//c.setLastName("test");
	   // c.equals(c);
	//	potentialMatchService.initializeMatch(a.getId(),null);
		return coachService.addCoach(a);
	}
	
	// Get all job types
	@GetMapping(path="/all")
	public @ResponseBody ResponseEntity<Iterable<Coach>> getAllCoach() {
            
            Iterable<Coach> allAList;
            allAList = coachService.getAllCoach();
		return new ResponseEntity<>(allAList,
                HttpStatus.OK);
	}
     
	// Get single job type by Id
	@GetMapping(path="/{id}")
	public @ResponseBody ResponseEntity<Optional<Coach>> getCoachById(@PathVariable(name = "id") Integer id) {
		return new ResponseEntity<>(coachService.getCoach(id),HttpStatus.OK);
	}
    
	// Get single job type by Email
	@GetMapping(path="")
	public @ResponseBody ResponseEntity<Optional<Coach>> getCoachByEmail(@RequestParam String email) {
		return new ResponseEntity<>(coachService.getCoachByEmail(email),HttpStatus.OK);
	}
	
	// Update a Job Type
	@PostMapping(path="/update")
	public @ResponseBody ResponseEntity<Object> updateCoach(@RequestBody 
        CoachDto a) {
		User user = userService.getUser(a.token);
		Optional<Coach> coach = coachService.getCoachByUserId(user.getId());
		
		if (coach.isPresent()) {
			coach.get().update(a);
		}
		
		return coachService.updateCoach(coach.get());
	}
	
	

	// Delete a Coach
	@RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String deleteCoach(@PathVariable(name = "id") Integer id) {
		return coachService.deleteCoach(id);
	}
}
