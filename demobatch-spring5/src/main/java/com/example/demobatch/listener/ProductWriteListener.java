package com.example.demobatch.listener;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.example.demobatch.controller.BatchDataEmitterFlux;
import com.example.demobatch.model.Product;

/**
 * Based on emitter, emit message using SseEmitter or Flux
 *
 */
@Component
public class ProductWriteListener implements ItemWriteListener<Product> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
 	@Autowired
 	public BatchDataEmitterFlux batchDataEmitterFlux; //spring 5
 	
    @Override
    public void beforeWrite(List items) {
    	log.info("Going to write following items: "+ items.toString());
    }
    
    @Override
    public void onWriteError(Exception exception, List items) {
    	log.info("Error occurred when writing items!");
    }
    
    @Override
    public void afterWrite(List items) {
    	log.info("After write emitting to client "+items.toString());

        //Spring 5
        try {
        	ServerSentEvent<String> itemsEvent = ServerSentEvent.builder(
        			items.toString()
            ).build();
        	
        	batchDataEmitterFlux.getEmitterFlux().onNext(itemsEvent);            
        } catch (Exception e) {
        	batchDataEmitterFlux.getEmitterFlux().onError(e);
        }    		
    }
}