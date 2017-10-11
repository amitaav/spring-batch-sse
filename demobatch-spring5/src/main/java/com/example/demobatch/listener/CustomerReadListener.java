package com.example.demobatch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.example.demobatch.model.Customer;

@Component
public class CustomerReadListener implements ItemReadListener<Customer>{
		private final Logger log = LoggerFactory.getLogger(this.getClass());
		
	    public void beforeRead() {
	    	log.info("Before reading an item");      
	    }
	    
	    @Override
	    public void afterRead(Customer item) {
	    	log.info("After reading an item: "+ item.toString());    
	    }

	    public void onReadError(Exception ex) {
	    	log.info("Error occurred while reading an item!");       
	    }

		
	}