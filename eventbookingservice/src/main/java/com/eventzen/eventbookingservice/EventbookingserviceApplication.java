package com.eventzen.eventbookingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventbookingserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventbookingserviceApplication.class, args);
		System.out.println("Event Booking Service Started!");
	}

}
