package com.cs7319.finalproject.broadcast_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BroadcastServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BroadcastServiceApplication.class, args);
	}

}
