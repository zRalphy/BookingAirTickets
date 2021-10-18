package com.pgs.booking.model.dto;

import com.pgs.booking.model.Flight;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateFlightDto {
    @NotNull(message = "Please enter your type.")
    Flight.TypeOfFlight type;
    @NotNull(message = "Please enter your departureDate.")
    LocalDateTime departureDate;
    @NotNull(message = "Please enter your dateOfArrival.")
    LocalDateTime arrivalDate;
}
