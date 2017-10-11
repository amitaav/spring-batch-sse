package com.example.demobatch.model;

import org.springframework.data.annotation.Id;

public class Customer {

	@Id
	private String id;

	private String email; 
	private String firstname;
	private String lastname;

	public Customer() {
	}

	public Customer(String email, String firstname, String lastname) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Override
	public String toString() {
		return String.format("Customer[id=%s, email='%s', firstname='%s', lastname='%s']", id,
				email, firstname, lastname);
	}

}