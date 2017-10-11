package com.example.demobatch.model;

import org.springframework.data.annotation.Id;

public class Product {

	@Id
	private String id;

	private String name; 
	private String description;

	public Product() {
	}

	public Product(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("Product[id=%s, name='%s', description='%s']", id,
				name, description);
	}

}