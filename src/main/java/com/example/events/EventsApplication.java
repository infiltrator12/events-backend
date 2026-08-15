package com.example.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class EventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsApplication.class, args);
		System.out.println(">> Server is up and running!");
	}

	//A simple test endpoint
	@GetMapping("/")
	public String home() {
		return "Hello! My Spring boot backend is successfully connected and running!";
	}


}
