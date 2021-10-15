package com.pgs.booking.model.dto;

import com.pgs.booking.model.Flight;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @Size(min = 3, max = 45, message = "DepartureAirport should have at least 3 and at most 45 characters.")
    String departureAirport;
    @Size(min = 3, max = 45, message = "ArrivalAirport should have at least 3 and at most 45 characters.")
    String arrivalAirport;
    @NotBlank(message = "Please enter your departureDate.")
    LocalDateTime departureDate;
    @NotBlank(message = "Please enter your dateOfArrival.")
    LocalDateTime arrivalDate;
}
