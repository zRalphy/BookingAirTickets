package com.pgs.booking.mappers.dto;

import com.pgs.booking.model.entity.Flight;
import com.pgs.booking.model.dto.FlightDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightDtoMapper {
    public List<FlightDto> mapToFlightsDto(List<Flight> flights) {
        return flights.stream()
                .map(this::mapToFlightDto)
                .collect(Collectors.toList());
    }

    public FlightDto mapToFlightDto(Flight flight) {
        return FlightDto.builder()
                .id(flight.getId())
                .type(flight.getType())
                .departureDate(flight.getDepartureDate())
                .arrivalDate(flight.getArrivalDate())
                .build();
    }
}
