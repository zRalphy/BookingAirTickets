package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.CreateUpdateAirportDto;
import com.pgs.booking.model.entity.Airport;
import org.springframework.stereotype.Component;

@Component
public class CreateUpdateAirportDtoMapper {

    public Airport mapToAirport(CreateUpdateAirportDto createUpdateAirportDto) {
        return Airport.builder()
                .code(createUpdateAirportDto.getCode())
                .name(createUpdateAirportDto.getName())
                .country(createUpdateAirportDto.getCountry())
                .build();
    }
}
