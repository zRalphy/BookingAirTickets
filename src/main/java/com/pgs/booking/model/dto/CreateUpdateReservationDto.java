package com.pgs.booking.model.dto;

import com.pgs.booking.model.Reservation;
import lombok.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateReservationDto {

    private Reservation.ReservationStatus status;
    private FlightDto flightDto;
    private Set<CreateUpdatePassengerDto> passengers;
}
