package com.pgs.booking.mappers;

import com.pgs.booking.model.entity.Passenger;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateUpdatePassengerDtoMapper {

    public List<Passenger> mapToPassengers(List<CreateUpdatePassengerDto> createUpdatePassengersDto) {
        return createUpdatePassengersDto.stream()
                .map(this::mapToPassenger)
                .collect(Collectors.toList());
    }

    public Passenger mapToPassenger(CreateUpdatePassengerDto createUpdatePassengerDto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(createUpdatePassengerDto.getFirstName());
        passenger.setLastName(createUpdatePassengerDto.getLastName());
        passenger.setEmail(createUpdatePassengerDto.getEmail());
        passenger.setCountry(createUpdatePassengerDto.getCountry());
        passenger.setTelephone(createUpdatePassengerDto.getTelephone());
        return passenger;
    }
}
