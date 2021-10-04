package com.pgs.BookingAirTickets;

import com.pgs.BookingAirTickets.helloController.HelloController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingAirTicketsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingAirTicketsApplication.class, args);
		HelloController helloController = new HelloController();
		helloController.index();
	}
}
