package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.entity.Flight;
import com.pgs.booking.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUpdateFlightDtoMapper {

    private  final AirportRepository airportRepository;
    public Flight mapToFlight(CreateUpdateFlightDto createUpdateFlightDto) {
        var departureAirport = airportRepository.findAirportByCode(createUpdateFlightDto.getDepartureAirportIataCode());
        var arrivalAirport = airportRepository.findAirportByCode(createUpdateFlightDto.getArrivalAirportIataCode());
        return Flight.builder()
                .type(createUpdateFlightDto.getType())
                .departureDate(createUpdateFlightDto.getDepartureDate())
                .arrivalDate(createUpdateFlightDto.getArrivalDate())
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .build();
    }
}
