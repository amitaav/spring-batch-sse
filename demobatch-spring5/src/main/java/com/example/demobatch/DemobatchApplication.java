package com.example.demobatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//explicit data source configuration for MongoDB using appllication.properties
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) 
public class DemobatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemobatchApplication.class, args);
	}
}
