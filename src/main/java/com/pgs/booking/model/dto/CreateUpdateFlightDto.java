package com.pgs.booking.model.dto;

import com.pgs.booking.model.entity.Flight;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "Please enter your departureAirportIataCode.")
    @Size(min = 1, max = 3, message = "DepartureAirportIataCode should have at least 1 and at most 3 characters.")
    private String departureAirportIataCode;
    @NotBlank(message = "Please enter your arrivalAirportIataCode.")
    @Size(min = 1, max = 3, message = "ArrivalAirportIataCode should have at least 1 and at most 3 characters.")
    private String arrivalAirportIataCode;
}
