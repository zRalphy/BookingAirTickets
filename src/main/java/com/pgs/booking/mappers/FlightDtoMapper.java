package com.pgs.booking.mappers;

import com.pgs.booking.model.Flight;
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
                .numberOfPassenger(flight.getNumberOfPassenger())
                .type(flight.getType())
                .typeOfSeat(flight.getTypeOfSeat())
                .typeOfLuggage(flight.getTypeOfLuggage())
                //.departureDate(flight.getDepartureDate())
                //.dateOfArrival(flight.getDateOfArrival())
                .build();
    }
}
