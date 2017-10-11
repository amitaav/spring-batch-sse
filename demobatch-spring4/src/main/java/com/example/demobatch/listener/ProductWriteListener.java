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

import com.example.demobatch.controller.BatchDataEmitter;
import com.example.demobatch.model.Product;

/**
 * Based on emitter, emit message using SseEmitter or Flux
 *
 */
@Component
public class ProductWriteListener implements ItemWriteListener<Product> {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
 	public BatchDataEmitter batchDataEmitter; //spring 4
 	
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

    	//Spring 4
    	SseEventBuilder builder = SseEmitter.event()
                .data(items.toString())
                .id("1")
                .reconnectTime(10_000L);
        		
        try {
        	SseEmitter sseEmitter = batchDataEmitter.getEmitter();       	
        	sseEmitter.send(builder);
		} catch (IOException e) {
			e.printStackTrace();
			batchDataEmitter.getEmitter().completeWithError(e);
		}        
    }
}