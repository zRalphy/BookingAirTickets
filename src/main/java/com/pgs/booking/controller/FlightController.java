package com.pgs.booking.controller;

import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/api/flights")
    public List<FlightDto> getFlights() {
        return flightService.getFlights();
    }

    @GetMapping("/api/flights/{id}")
    public FlightDto getSingleFlight(@Valid @PathVariable long id)  {
        return flightService.getSingleFlight(id);
    }
/*
    @PostMapping("/api/flights")
    public FlightDto addFlight(@Valid @RequestBody CreateUpdateFlightDto createUpdateFlightDto) {
        return flightService.addFlight(createUpdateFlightDto);
    }

    @PutMapping("/api/flights/{id}")
    public FlightDto editFlight(@Valid @RequestBody CreateUpdateFlightDto createUpdateFlightDto, @PathVariable long id) {
        return flightService.editFlight(id, createUpdateFlightDto);
    }

    @DeleteMapping("/api/flights/{id}")
    public void deleteFlight(@PathVariable("id") long id) {
        flightService.deleteFlight(id);
    }

 */
}
