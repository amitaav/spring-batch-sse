package com.example.demobatch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {

	@Autowired
	public BatchDataEmitter batchDataEmitter;
	
    @GetMapping(path = "/sse/batchdata", produces = "text/event-stream")
    public SseEmitter getBatchData() {
        return batchDataEmitter.getEmitter();
    }
}
