/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.persistence.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.baeldung.persistence.model.PotentialMatch;
/**
 *
 * @author kolby
 */

public interface PotentialMatchRepository extends CrudRepository<PotentialMatch, Integer>{

	
 //@Query(value= "Select * from client", nativeQuery=true)
// public List<Client> getAll();
 @Query(value ="Select client_id from potential_match C\r\n"
 		+ "Where client_swiped =0 and coach_swiped !=-1 and client_id = :id\r\n", nativeQuery = true)
 public ArrayList<Integer> getAllPotentialClientMatch(@Param("id") Integer id);
 
 @Query(value ="Select client_id from potential_match C\r\n"
	 		+ "Where coach_swiped =0 and client_swiped == 1 client_id = :id\r\n", nativeQuery = true)
	 public List<Integer> getAllPotentialCoachMatch(@Param("id") Integer id);
 
 @Query(value ="Select coach_swiped,client_swiped from potential_match C\r\n"
	 		+ "Where coach_id =:coach_id and client_id=:client_id\r\n", nativeQuery = true)
	 public List<Integer> checkMatch(@Param("coach_id") Integer coach_id,@Param("client_id") Integer client_id);
 
 @Query(value ="Select client_swiped from potential_match C\r\n"
	 		+ "Where coach_id =:coach_id and client_id=:client_id\r\n", nativeQuery = true)
	 public List<Integer> getMatchedClient(@Param("coach_id") Integer coach_id,@Param("client_id") Integer client_id);
 
}
