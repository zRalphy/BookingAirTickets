package com.pgs.booking.mappers;

import com.pgs.booking.model.Flight;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateUpdateFlightDtoMapper {

    public List<CreateUpdateFlightDto> mapToPassengersDto(List<Flight> flights) {
        return flights.stream()
                .map(this::mapToCreateFlightDto)
                .collect(Collectors.toList());
    }

    public CreateUpdateFlightDto mapToCreateFlightDto(Flight flight) {
        return CreateUpdateFlightDto.builder()
                .type(flight.getType())
                .departureDate(flight.getDepartureDate())
                .arrivalDate(flight.getArrivalDate())
                .build();
    }

    public Flight mapToFlight(CreateUpdateFlightDto createUpdateFlightDto) {
        Flight flight = new Flight();
        flight.setType(createUpdateFlightDto.getType());
        flight.setDepartureDate(createUpdateFlightDto.getDepartureDate());
        flight.setArrivalDate(createUpdateFlightDto.getArrivalDate());
        return flight;
    }
}
