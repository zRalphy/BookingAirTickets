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

    public List<Flight> mapToFlights(List<CreateUpdateFlightDto> createUpdateFlightsDto) {
        return createUpdateFlightsDto.stream()
                .map(this::mapToFlight)
                .collect(Collectors.toList());
    }

    public CreateUpdateFlightDto mapToCreateFlightDto(Flight flight) {
        return CreateUpdateFlightDto.builder()
                .numberOfPassenger(flight.getNumberOfPassenger())
                .type(flight.getType())
                .typeOfSeat(flight.getTypeOfSeat())
                .typeOfLuggage(flight.getTypeOfLuggage())
                .departureDate(flight.getDepartureDate())
                .dateOfArrival(flight.getDateOfArrival())
                .build();
    }

    public Flight mapToFlight(CreateUpdateFlightDto createUpdateFlightDto) {
        Flight flight = new Flight();
        flight.setNumberOfPassenger(createUpdateFlightDto.getNumberOfPassenger());
        flight.setType(createUpdateFlightDto.getType());
        flight.setTypeOfSeat(createUpdateFlightDto.getTypeOfSeat());
        flight.setTypeOfLuggage(createUpdateFlightDto.getTypeOfLuggage());
        flight.setDepartureDate(createUpdateFlightDto.getDepartureDate());
        flight.setDateOfArrival(createUpdateFlightDto.getDateOfArrival());
        return flight;
    }
}
