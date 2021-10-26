package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/flights/{id}")
    public List<ReservationDto> getReservationByFlight(@PathVariable long id) {
        return reservationService.getReservationByFlight(id);
    }

    @GetMapping("/users/{id}")
    public List<ReservationDto> getReservationByUser(@PathVariable long id) {
        return reservationService.getReservationByUser(id);
    }

    @PostMapping
    public ReservationDto addReservation(@Valid @RequestBody CreateUpdateReservationDto createUpdatePassengerDto,
                                         PreAuthenticatedAuthenticationToken authenticationToken) {
        if (authenticationToken.getPrincipal() instanceof User user) {
            return reservationService.addReservation(createUpdatePassengerDto, user);
        }
        throw new IllegalStateException("The token does not contain authorized user data.");
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
    }

    @PutMapping("/{id}/realized")
    public ReservationDto realizedReservation(@PathVariable Long id) {
        return reservationService.realizedReservation(id);
    }

    @PutMapping("/{id}/canceled")
    public ReservationDto canceledReservation(@PathVariable Long id) {
        return reservationService.canceledReservation(id);
    }
}
