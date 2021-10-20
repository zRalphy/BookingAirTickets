package com.pgs.booking.model.dto;

import com.pgs.booking.model.Flight;
import com.pgs.booking.model.Reservation;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateReservationDto {
    @NotNull
    private Reservation.ReservationStatus status;
    @NotNull
    private Flight flight;
    private List<@NotNull CreateUpdatePassengerDto> passengers;
}
