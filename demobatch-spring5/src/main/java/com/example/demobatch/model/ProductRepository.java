package com.example.demobatch.model;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

	Customer findByName(String name);
	
	Customer findByDescription(String description);
}
