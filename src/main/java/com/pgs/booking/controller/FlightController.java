package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping
    public List<FlightDto> getFlights() {
        return flightService.getFlights();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/{id}")
    public FlightDto getFlight(@PathVariable long id)  {
        return flightService.getFlight(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @PostMapping
    public FlightDto addFlight(@Valid @RequestBody CreateUpdateFlightDto createUpdateFlightDto) {
        return flightService.addFlight(createUpdateFlightDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @PutMapping("/{id}")
    public FlightDto editFlight(@Valid @RequestBody CreateUpdateFlightDto createUpdateFlightDto, @PathVariable long id) {
        return flightService.editFlight(id, createUpdateFlightDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable("id") long id) {
        flightService.deleteFlight(id);
    }
}
