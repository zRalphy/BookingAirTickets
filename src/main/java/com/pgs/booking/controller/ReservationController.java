package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/flight{id}")
    public List<ReservationDto> getReservationByFlight(@PathVariable long id) {
        return reservationService.getReservationByFlight(id);
    }


    @Transient
    //NOT IMPLEMENTED YET
    @GetMapping("/user{id}")
    public  List<ReservationDto> getReservationByUser(@PathVariable long id) {
        return reservationService.getReservationByUser(id);
    }

    @PostMapping
    public ReservationDto addReservation(@Valid @RequestBody CreateUpdateReservationDto createUpdatePassengerDto) {
        return reservationService.addReservation(createUpdatePassengerDto);
    }
}