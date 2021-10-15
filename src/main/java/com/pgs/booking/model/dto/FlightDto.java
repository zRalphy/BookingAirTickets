package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;
import java.time.LocalDateTime;

@Value
@Builder
public class FlightDto {
    Long id;
    String numberOfPassenger;
    String type;
    String typeOfSeat;
    String typeOfLuggage;
    LocalDateTime departureDate;
    LocalDateTime dateOfArrival;
}
