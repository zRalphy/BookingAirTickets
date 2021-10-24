package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AirportDto {
    Long id;
    String departureAirportCode;
    String arrivalAirportCode;
    String departureAirportName;
    String arrivalAirportName;
    String country;
    String city;
}
