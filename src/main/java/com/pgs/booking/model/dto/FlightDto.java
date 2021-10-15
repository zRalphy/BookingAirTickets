package com.pgs.booking.model.dto;

import com.pgs.booking.model.Flight;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class FlightDto {
    Long id;
    Flight.TypeOfFlight type;
    String departureAirport;
    String arrivalAirport;
    LocalDateTime departureDate;
    LocalDateTime arrivalDate;
}
