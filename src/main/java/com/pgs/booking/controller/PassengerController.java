package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.service.PassengerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@OpenAPIDefinition
@RestController
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping
    public List<PassengerDto> getPassengers() {
        log.trace("Controller method: getPassengers.");
        return passengerService.getPassengers();
    }

    @GetMapping("/{id}")
    public PassengerDto getSinglePassenger(@Valid @PathVariable long id) {
        log.trace("Controller method: getSinglePassenger.");
        return passengerService.getSinglePassenger(id);
    }

    /**
     * @deprecated add passengers via `POST /api/reservations/`
     */
    @Deprecated(forRemoval = true)
    @PostMapping
    public PassengerDto addPassenger(@Valid @RequestBody CreateUpdatePassengerDto createUpdatePassengerDto) {
        log.trace("Controller method: addPassenger.");
        return passengerService.addPassenger(createUpdatePassengerDto);
    }

    @PutMapping("/{id}")
    public PassengerDto editPassenger(@Valid @RequestBody CreateUpdatePassengerDto createUpdatePassengerDto, @PathVariable long id) {
        log.trace("Controller method: editPassenger.");
        return passengerService.editPassenger(id, createUpdatePassengerDto);
    }
}
