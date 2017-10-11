package com.example.demobatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class SseController {

	@Autowired
	public BatchDataEmitterFlux batchDataEmitterFlux;
	
    @GetMapping(path = "/sse/batchdata", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> getBatchData() {
        return batchDataEmitterFlux.getEmitterFlux().log();
    }
}
