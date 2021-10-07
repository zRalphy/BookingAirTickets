package com.pgs.booking.controller;

import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/api/passengers")
    public List<PassengerDto> getPassengers() {
        return passengerService.getPassengers();
    }

    @GetMapping("/api/passengers/{id}")
    public PassengerDto getSinglePassenger(@PathVariable long id)  {
            return passengerService.getSinglePassenger(id);
    }
/*
    @PostMapping("/api/passengers")
    public void addPassenger(@RequestBody PassengerDto passengerDto) {
        passengerService.addNewPassenger(passengerDto);
    }

 */
/*
    @PutMapping("/api/passengers/{id}")
    public Passenger editPassenger(@RequestBody PassengerDto passengerDto) {
        return passengerService.editPassenger(passengerDto);
    }

 */
}
