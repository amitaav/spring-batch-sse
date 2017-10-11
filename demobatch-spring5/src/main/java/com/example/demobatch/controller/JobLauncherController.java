package com.example.demobatch.controller;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demobatch.config.BatchConfig;
import com.example.demobatch.listener.JobCompletionNotificationListener;

@RestController
public class JobLauncherController {
 
    @Autowired
    JobLauncher jobLauncher;
 
    @Autowired
    BatchConfig batchConfig;
    
    @Autowired
    JobCompletionNotificationListener jobCompletionNotificationListener;

    @GetMapping("/loadcustomers")
    public String handleCustomersWithEmitter() throws Exception {
 
    	final Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        	
			JobExecution execution = jobLauncher.run(batchConfig.importUserJob(jobCompletionNotificationListener, "customer"), jobParameters);
			
			logger.info("Exit Status for customers: " + execution.getStatus());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
 
        return "Done with customers";
    }
    
    @GetMapping("/loadproducts")
    public String handleProductsWithEmitter() throws Exception {
 
    	final Logger logger = LoggerFactory.getLogger(this.getClass());

        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        	
			JobExecution execution = jobLauncher.run(batchConfig.importUserJob(jobCompletionNotificationListener, "product"), jobParameters);
			
			logger.info("Exit Status for products: " + execution.getStatus());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
 
        return "Done with products";
    }    
}