package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public List<FlightDto> getFlights() {
        return flightService.getFlights();
    }

    @GetMapping("/{id}")
    public FlightDto getFlight(@PathVariable long id)  {
        return flightService.getSingleFlight(id);
    }

    @PostMapping
    public FlightDto addFlight(@Valid @RequestBody CreateUpdateFlightDto createUpdateFlightDto) {
        return flightService.addFlight(createUpdateFlightDto);
    }

    @PutMapping("/{id}")
    public FlightDto editFlight(@Valid @RequestBody CreateUpdateFlightDto createUpdateFlightDto, @PathVariable long id) {
        return flightService.editFlight(id, createUpdateFlightDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable("id") long id) {
        flightService.deleteFlight(id);
    }
}
