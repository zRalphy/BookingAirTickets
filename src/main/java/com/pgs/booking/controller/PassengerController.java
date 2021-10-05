package com.pgs.booking.controller;

import com.pgs.booking.model.Passenger;
import com.pgs.booking.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/api/passengers")
    public List<Passenger> getPassengers() {
        return passengerService.getPassengers();
    }

    @GetMapping("/api/passengers/{id}")
    public Passenger getSinglePassenger(@PathVariable long id) {
        return passengerService.getSinglePassenger(id);
    }

    @PostMapping("/api/passengers")
    public void addPassenger(@RequestBody Passenger passenger) {
        passengerService.addNewPassenger(passenger);
    }

    @PutMapping("/api/passengers/{id}")
    public Passenger editPassenger(@RequestBody Passenger passenger) {
        return passengerService.editPassenger(passenger);
    }
}
