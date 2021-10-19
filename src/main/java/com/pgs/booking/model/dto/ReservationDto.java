package com.pgs.booking.model.dto;

import com.pgs.booking.model.Flight;
import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.Reservation;
import lombok.Builder;
import lombok.Value;
import java.util.Set;

@Value
@Builder
public class ReservationDto {
    Long id;
    Reservation.TypeOfReservation status;
    Flight flight;
    Set<Passenger> passengers;
}
