package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping
    public List<ReservationDto> getReservations() {
        return reservationService.getReservations();
    }

    @GetMapping("/{id}")
    public ReservationDto getReservation(@PathVariable long id)  {
        return reservationService.getReservation(id);
    }

    @PostMapping
    public ReservationDto addReservation(@Valid @RequestBody CreateUpdateReservationDto createUpdateReservationDto) {
        return reservationService.addReservation(createUpdateReservationDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") long id) {
        reservationService.deleteReservation(id);
    }
}