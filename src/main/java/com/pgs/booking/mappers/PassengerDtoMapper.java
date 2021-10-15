package com.pgs.booking.mappers;

import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.dto.PassengerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PassengerDtoMapper {

	public List<PassengerDto> mapToPassengersDto(List<Passenger> passengers) {
		return passengers.stream()
				.map(this::mapToPassengerDto)
				.collect(Collectors.toList());
	}

	public PassengerDto mapToPassengerDto(Passenger passenger) {
		return PassengerDto.builder()
				.id(passenger.getId())
				.firstName(passenger.getFirstName())
				.lastName(passenger.getLastName())
				.email(passenger.getEmail())
				.country(passenger.getCountry())
				.telephone(passenger.getTelephone())
				.build();
	}
}

