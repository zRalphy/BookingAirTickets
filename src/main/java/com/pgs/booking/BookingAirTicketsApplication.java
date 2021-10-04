package com.pgs.booking;

import com.pgs.booking.controller.Controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingAirTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingAirTicketsApplication.class, args);
		Controller controller = new Controller();
		controller.index();
	}
}
