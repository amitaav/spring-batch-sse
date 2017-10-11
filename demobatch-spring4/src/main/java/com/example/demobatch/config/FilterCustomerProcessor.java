package com.example.demobatch.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demobatch.model.Customer;

//process item before writing to MongoDB
@Component
public class FilterCustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		if(item.getEmail().endsWith("example.com")) {
			item.setEmail(item.getEmail().replace("example.com", "changedexample.com"));
			return item;
		}
		return item;
	}

}