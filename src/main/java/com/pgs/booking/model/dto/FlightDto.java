package com.pgs.booking.model.dto;

import com.pgs.booking.model.entity.Flight;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class FlightDto {
    Long id;
    Flight.TypeOfFlight type;
    LocalDateTime departureDate;
    LocalDateTime arrivalDate;
}
