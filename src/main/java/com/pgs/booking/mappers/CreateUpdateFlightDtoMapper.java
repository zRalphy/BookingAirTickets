package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.entity.Flight;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdateFlightDtoMapper {

    public Flight mapToFlight(CreateUpdateFlightDto createUpdateFlightDto) {
        return Flight.builder()
                .type(createUpdateFlightDto.getType())
                .departureDate(createUpdateFlightDto.getDepartureDate())
                .arrivalDate(createUpdateFlightDto.getArrivalDate())
                .departureAirportIataCode(createUpdateFlightDto.getDepartureAirportIataCode())
                .arrivalAirportIataCode(createUpdateFlightDto.getArrivalAirportIataCode())
                .build();
    }
}
