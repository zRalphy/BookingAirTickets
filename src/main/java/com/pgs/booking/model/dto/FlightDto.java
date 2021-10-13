package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;
import java.util.Date;

@Value
@Builder
public class FlightDto {
    Long id;
    String numberOfPassenger;
    String type;
    String typeOfSeat;
    Date typeOfLuggage;
    String departureDate;
    Date dateOfArrival;
}
