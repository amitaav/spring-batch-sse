package com.example.demobatch.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	Customer findByEmail(String email);
	
	Customer findByFirstname(String firstname);

	List<Customer> findByLastname(String lastname);

}
