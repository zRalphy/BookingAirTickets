package com.pgs.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class BookingAirTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingAirTicketsApplication.class, args);
	}
}
