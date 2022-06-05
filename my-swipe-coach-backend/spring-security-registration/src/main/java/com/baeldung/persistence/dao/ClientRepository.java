/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.persistence.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.baeldung.persistence.model.Client;
/**
 *
 * @author kolby
 */

public interface ClientRepository extends CrudRepository<Client, Integer>{
	
	//@Query(value= "Select * from client Join", nativeQuery=true)
	Optional<Client> findByUserId(Long userId);

	Optional<Client> findByEmail(String email);

	
 //@Query(value= "Select * from client", nativeQuery=true)
// public List<Client> getAll();
 
}
