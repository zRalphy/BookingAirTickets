package com.pgs.booking.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateAirportDto {
    @NotBlank(message = "Please enter your departureAirportCode.")
    @Size(min = 3, max = 6, message = "DepartureAirportCode should have at least 3 and at most 6 characters.")
    private String departureAirportCode;
    @NotBlank(message = "Please enter your arrivalAirportCode.")
    @Size(min = 3, max = 6, message = "ArrivalAirportCode should have at least 3 and at most 6 characters.")
    private String arrivalAirportCode;
    @NotBlank(message = "Please enter your departureAirportName.")
    @Size(min = 5, max = 45, message = "DepartureAirportName should have at least 5 and at most 45 characters.")
    private String departureAirportName;
    @NotBlank(message = "Please enter your arrivalAirportName.")
    @Size(min = 5, max = 45, message = "ArrivalAirportName should have at least 5 and at most 45 characters.")
    private String arrivalAirportName;
    @NotBlank(message = "Please enter your airport country.")
    @Size(min = 3, max = 45, message = "Country should have at least 3 and at most 45 characters.")
    private String country;
    @NotBlank(message = "Please enter your airport city.")
    @Size(min = 3, max = 45, message = "City should have at least 3 and at most 45 characters.")
    private String city;
}
