package com.pgs.booking.model.dto;

import com.pgs.booking.model.Reservation;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class ReservationDto {
    Long id;
    Reservation.ReservationStatus status;
    FlightDto flightDto;
    Set<CreateUpdatePassengerDto> passengers;
}
