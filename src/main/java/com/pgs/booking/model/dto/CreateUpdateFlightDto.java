package com.pgs.booking.model.dto;

import com.pgs.booking.model.Flight;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateFlightDto {

    @NotBlank(message = "Please enter your type.")
    Flight.TypeOfFlight type;
    @NotBlank(message = "Please enter your departureDate.")
    LocalDateTime departureDate;
    @NotBlank(message = "Please enter your dateOfArrival.")
    LocalDateTime arrivalDate;
}
