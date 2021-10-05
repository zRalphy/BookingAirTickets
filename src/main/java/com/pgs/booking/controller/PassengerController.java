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
    public Passenger getSinglePassenger(@PathVariable Long id) {
        return passengerService.getSinglePassenger(id);
    }

    @PostMapping("/api/passengers")
    public void addPassenger(@RequestBody Passenger passenger) {
        passengerService.addNewPassenger(passenger);
    }

    @PutMapping(path = "/api/passengers/{id}")
    public void updatePassenger(
            @PathVariable ("id") Long id,
            @RequestParam (required = false) String firstName,
            @RequestParam (required = false) String lastName,
            @RequestParam (required = false) String email,
            @RequestParam (required = false) String country,
            @RequestParam (required = false) String telephone
    ){
        passengerService.updatePassenger(id, firstName, lastName, email, country, telephone);
    }
}
