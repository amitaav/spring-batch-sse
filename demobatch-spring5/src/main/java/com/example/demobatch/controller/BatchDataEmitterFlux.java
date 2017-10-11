package com.example.demobatch.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;

import reactor.core.publisher.EmitterProcessor;

@Component
public class BatchDataEmitterFlux {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final EmitterProcessor<ServerSentEvent<String>> batchDataEmitterFlux = EmitterProcessor.create();
	
	public EmitterProcessor<ServerSentEvent<String>> getEmitterFlux() {
		logger.info("Getting Flux based Emitter>>"+this.batchDataEmitterFlux.toString());
		
        return batchDataEmitterFlux;
    } 
}