package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.service.ReservationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@OpenAPIDefinition
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "Get all reservation for flight with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all reservations"),
            @ApiResponse(responseCode = "400", description = "Invalid flight id supplied"),
            @ApiResponse(responseCode = "401", description = "User unauthorized for this action")
    })
    @GetMapping("/flights/{id}")
    public List<ReservationDto> getReservationsByFlight(@PathVariable long id) {
        return reservationService.getReservationsByFlight(id);
    }

    @Operation(summary = "Get all reservation for current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all reservation for this user"),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied"),
            @ApiResponse(responseCode = "401", description = "User unauthorized for this action")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @GetMapping("/users")
    public List<ReservationDto> getReservationsForCurrentUser(@PathParam("authenticationToken") PreAuthenticatedAuthenticationToken authenticationToken) {
        if (authenticationToken.getPrincipal() instanceof User user) {
            return reservationService.getReservationsByCurrentUser(user);
        }
        throw new IllegalStateException("The token does not contain authorized user data.");
    }

    @Operation(summary = "Get reservation for User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all reservation for user"),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied"),
            @ApiResponse(responseCode = "401", description = "User unauthorized for this action")
    })
    @GetMapping("/users/{id}")
    public List<ReservationDto> getReservationsByUser(@PathVariable long id) {
        return reservationService.getReservationsByUser(id);
    }

    @Operation(summary = "Add reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added reservation"),
            @ApiResponse(responseCode = "400", description = "Invalid data passed"),
            @ApiResponse(responseCode = "401", description = "User unauthorized for this action")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
    @PostMapping
    public ReservationDto addReservation(@Valid @RequestBody CreateUpdateReservationDto createUpdatePassengerDto,
                                         PreAuthenticatedAuthenticationToken authenticationToken) {
        if (authenticationToken.getPrincipal() instanceof User user) {
            return reservationService.addReservation(createUpdatePassengerDto, user);
        }
        throw new IllegalStateException("The token does not contain authorized user data.");
    }

    @Operation(summary = "Delete reservation by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted reservation"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation id supplied"),
            @ApiResponse(responseCode = "401", description = "User unauthorized for this action")
    })
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
    }

    @Operation(summary = "Change reservation status with given id to REALIZED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed reservation status to REALIZED"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation id supplied"),
            @ApiResponse(responseCode = "401", description = "User unauthorized for this action")
    })
    @PutMapping("/{id}/realized")
    public ReservationDto realizedReservation(@PathVariable Long id) {
        return reservationService.realizedReservation(id);
    }

    @Operation(summary = "Change reservation status with given id to CANCELED")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed reservation status to CANCELED"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation id supplied"),
            @ApiResponse(responseCode = "401", description = "User unauthorized for this action")
    })
    @PutMapping("/{id}/canceled")
    public ReservationDto canceledReservation(@PathVariable Long id) {
        return reservationService.canceledReservation(id);
    }
}
