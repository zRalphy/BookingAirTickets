package com.pgs.booking.model.dto;

import com.pgs.booking.model.entity.Flight;
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
    private Flight.TypeOfFlight type;
    @NotNull(message = "Please enter your departureDate.")
    private LocalDateTime departureDate;
    @NotNull(message = "Please enter your dateOfArrival.")
    private LocalDateTime arrivalDate;
}
