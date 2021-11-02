package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.AirportDto;
import com.pgs.booking.model.entity.Airport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AirportDtoMapper {

    public List<AirportDto> mapToAirportsDto(List<Airport> airports) {
        return airports.stream()
                .map(this::mapToAirportDto)
                .collect(Collectors.toList());
    }

    public AirportDto mapToAirportDto(Airport airport) {
        return AirportDto.builder()
                .id(airport.getId())
                .code(airport.getCode())
                .name(airport.getName())
                .country(airport.getCountry())
                .build();
    }
}
