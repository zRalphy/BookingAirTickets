package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.service.ReservationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // MARK: - GETTING METHODS

    @Operation(summary = "Get all reservation for flight with given id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all reservations"),
        @ApiResponse(responseCode = "404", description = "Selected flight was not found")
    })
    @GetMapping("/flights/{id}")
    public List<ReservationDto> getReservationsByFlight(@PathVariable long id) {
        return reservationService.getReservationsByFlight(id);
    }

    @Operation(summary = "Get all reservation for current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all reservation for this user")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @GetMapping("/users")
    public List<ReservationDto> getReservationsForCurrentUser(Authentication authentication) {
        if (authentication.getPrincipal() instanceof User user) {
            return reservationService.getReservationsByCurrentUser(user);
        }
        throw new IllegalStateException("The token does not contain authorized user data.");
    }

    @Operation(summary = "Get reservation for User")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found all reservation for user"),
        @ApiResponse(responseCode = "404", description = "Selected user was not found")
    })
    @GetMapping("/users/{id}")
    public List<ReservationDto> getReservationsByUser(@PathVariable long id) {
        return reservationService.getReservationsByUser(id);
    }

    // MARK: - ADDING METHODS

    @Operation(summary = "Add reservation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Added reservation"),
        @ApiResponse(responseCode = "404", description = "Selected flight for reservation was not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @PostMapping
    public ReservationDto addReservation(@Valid @RequestBody CreateUpdateReservationDto createUpdatePassengerDto,
                                         Authentication authentication) {
        if (authentication.getPrincipal() instanceof User user) {
            return reservationService.addReservation(createUpdatePassengerDto, user);
        }
        throw new IllegalStateException("The token does not contain authorized user data.");
    }

    // MARK: - DELETE METHODS

    @Operation(summary = "Delete reservation by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Deleted reservation"),
        @ApiResponse(responseCode = "404", description = "Selected reservation was not found")
    })
    @DeleteMapping("/{id}")
    public String deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        return "Success delete reservation with id: " + id;
    }

    // MARK: - PUT METHODS

    @Operation(summary = "Change reservation status with given id to REALIZED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Changed reservation status to REALIZED"),
        @ApiResponse(responseCode = "404", description = "Selected reservation was not found")
    })
    @PutMapping("/{id}/realized")
    public ReservationDto realizedReservation(@PathVariable Long id) {
        return reservationService.realizedReservation(id);
    }

    @Operation(summary = "Change reservation status with given id to CANCELED")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Changed reservation status to CANCELED"),
        @ApiResponse(responseCode = "404", description = "Selected reservation was not found")
    })
    @PutMapping("/{id}/canceled")
    public ReservationDto canceledReservation(@PathVariable Long id) {
        return reservationService.canceledReservation(id);
    }
}
