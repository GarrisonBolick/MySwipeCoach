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

import com.baeldung.persistence.model.Client;
import com.baeldung.persistence.model.User;
import com.baeldung.service.ClientService;
import com.baeldung.service.PotentialMatchService;
import com.baeldung.service.UserService;
import com.baeldung.web.dto.ClientDto;

@RestController
@RequestMapping(path="/client")
@CrossOrigin
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private PotentialMatchService potentialMatchService;
	
	@Autowired
	private UserService userService;

	// Add new job type
	@PostMapping(path="/add")
	public @ResponseBody String addNewClient (@RequestBody Client a) {
		//Client c = new Client();
		//c.setId(3);
		//c.setFirstName("test");
		//c.setLastName("test");
	   // c.equals(c);
	//	potentialMatchService.initializeMatch(a.getId(),null);
		return clientService.addClient(a);
	}
	
	// Get all job types
	@GetMapping(path="/all")
	public @ResponseBody ResponseEntity<Iterable<Client>> getAllClient() {
            
            Iterable<Client> allAList;
            allAList = clientService.getAllClient();
		return new ResponseEntity<>(allAList,
                HttpStatus.OK);
	}
     
	// Get single job type by Id
	@GetMapping(path="/{id}")
	public @ResponseBody ResponseEntity<Optional<Client>> getClientById(@PathVariable(name = "id") Integer id) {
		return new ResponseEntity<>(clientService.getClient(id),HttpStatus.OK);
	}
    
	// Get single job type by Email
	@GetMapping(path="")
	public @ResponseBody ResponseEntity<Optional<Client>> getClientByEmail(@RequestParam String email) {
		return new ResponseEntity<>(clientService.getClientByEmail(email),HttpStatus.OK);
	}
	
	// Update a Job Type
	@PostMapping(path="/update")
	public @ResponseBody ResponseEntity<Object> updateClient(@RequestBody 
        ClientDto a) {
		User user = userService.getUser(a.token);
		Optional<Client> client = clientService.getClientByUserId(user.getId());
		
		if (client.isPresent()) {
			client.get().update(a);
		}
		
		return clientService.updateClient(client.get());
	}
	
	

	// Delete a Client
	@RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String deleteClient(@PathVariable(name = "id") Integer id) {
		return clientService.deleteClient(id);
	}
}
