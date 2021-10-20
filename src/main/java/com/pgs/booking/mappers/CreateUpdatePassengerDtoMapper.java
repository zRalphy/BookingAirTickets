package com.pgs.booking.mappers;

import com.pgs.booking.model.entity.Passenger;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateUpdatePassengerDtoMapper {

    public List<CreateUpdatePassengerDto> mapToCreatePassengersDto(List<Passenger> passengers) {
        return passengers.stream()
                .map(this::mapToCreatePassengerDto)
                .collect(Collectors.toList());
    }

    public List<Passenger> mapToPassengers(List<CreateUpdatePassengerDto> createUpdatePassengersDto) {
        return createUpdatePassengersDto.stream()
                .map(this::mapToPassenger)
                .collect(Collectors.toList());
    }

    public CreateUpdatePassengerDto mapToCreatePassengerDto(Passenger passenger) {
        return CreateUpdatePassengerDto.builder()
                .firstName(passenger.getFirstName())
                .lastName(passenger.getLastName())
                .email(passenger.getEmail())
                .country(passenger.getCountry())
                .telephone(passenger.getTelephone())
                .build();
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
