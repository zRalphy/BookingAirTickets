package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.model.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FlightDtoMapper {

    private final AirportDtoMapper airportDtoMapper;

    public List<FlightDto> mapToFlightsDto(List<Flight> flights) {
        return flights.stream()
                .map(this::mapToFlightDto)
                .collect(Collectors.toList());
    }

    public FlightDto mapToFlightDto(Flight flight) {
        var departureAirport = flight.getDepartureAirport();
        var arrivalAirport = flight.getArrivalAirport();
        var departureAirportIataCode = airportDtoMapper.mapToAirportDto(departureAirport).getCode();
        var arrivalAirportIataCode = airportDtoMapper.mapToAirportDto(arrivalAirport).getCode();
        return FlightDto.builder()
                .id(flight.getId())
                .type(flight.getType())
                .departureDate(flight.getDepartureDate())
                .arrivalDate(flight.getArrivalDate())
                .departureAirportIataCode(departureAirportIataCode)
                .arrivalAirportIataCode(arrivalAirportIataCode)
                .build();
    }
}
