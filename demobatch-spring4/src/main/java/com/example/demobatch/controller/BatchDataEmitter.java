package com.example.demobatch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
public class BatchDataEmitter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final SseEmitter batchDataEmitter = new SseEmitter();
	
	public SseEmitter getEmitter() {
		logger.info("Getting SseEmitter>>"+this.batchDataEmitter.toString());

        return batchDataEmitter;
    } 
}