package com.pgs.booking.model.dto;

import com.pgs.booking.model.Reservation;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateReservationDto {

    private Reservation.ReservationStatus status;
    private Long flight_id;
    private List<CreateUpdatePassengerDto> passengers;
}
