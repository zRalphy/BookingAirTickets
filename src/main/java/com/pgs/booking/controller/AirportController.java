package com.pgs.booking.controller;

import com.pgs.booking.model.dto.AirportDto;
import com.pgs.booking.model.dto.CreateUpdateAirportDto;
import com.pgs.booking.service.AirportService;
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
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @GetMapping("/")
    public List<AirportDto> getAirports() {
        return airportService.getAirports();
    }

    @GetMapping("/{id}")
    public AirportDto getAirportById(@PathVariable long id)  {
        return airportService.getAirport(id);
    }

    @GetMapping
    public AirportDto getAirportByCode(@RequestParam("code") String code)  {
        return airportService.getAirportByCode(code);
    }

    @PostMapping
    public AirportDto addAirport(@Valid @RequestBody CreateUpdateAirportDto createUpdateAirportDto) {
        return airportService.addAirport(createUpdateAirportDto);
    }

    @PutMapping("/{id}")
    public AirportDto editAirport(@Valid @RequestBody CreateUpdateAirportDto createUpdateAirportDto, @PathVariable long id) {
        return airportService.editAirport(id, createUpdateAirportDto);
    }
}
