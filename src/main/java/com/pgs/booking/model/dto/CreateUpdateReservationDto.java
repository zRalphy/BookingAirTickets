package com.pgs.booking.model.dto;

import com.pgs.booking.model.Reservation;
import lombok.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateReservationDto {
    @NotNull(message = "Please enter your status.")
    Reservation.TypeOfReservation status;
    private CreateUpdateFlightDto createUpdateFlightDto;
}
