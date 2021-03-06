package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.service.FlightService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@OpenAPIDefinition
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public List<FlightDto> getFlights() {
        return flightService.getFlights();
    }

    @GetMapping("/{id}")
    public FlightDto getFlight(@PathVariable long id)  {
        return flightService.getFlight(id);
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
    public String deleteFlight(@PathVariable("id") long id) {
        flightService.deleteFlight(id);
        return "Success delete flight with id: " + id;
    }
}
