package com.pgs.BookingAirTickets.helloController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public void index() {
        System.out.println("Greetings from Spring Boot!");
    }
}
