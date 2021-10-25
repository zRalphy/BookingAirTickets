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
    @NotBlank(message = "Please enter your departureAirport.")
    @Size(min = 3, max = 45, message = "DepartureAirport should have at least 3 and at most 45 characters.")
    private String departureAirport;
    @NotBlank(message = "Please enter your arrivalAirport.")
    @Size(min = 3, max = 45, message = "ArrivalAirport should have at least 3 and at most 45 characters.")
    private String arrivalAirport;
}
