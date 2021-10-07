package com.pgs.booking.controller;

import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.dto.PassengerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PassengerDtoMapper {

    public PassengerDto mapToPassengerDto(Passenger passenger) {
		PassengerDto passengerDto = new PassengerDto();
		passengerDto.setFirstName(passenger.getFirstName());
		passengerDto.setLastName(passenger.getLastName());
	    passengerDto.setEmail(passenger.getEmail());
	    passengerDto.setCountry(passenger.getCountry());
	    passengerDto.setTelephone(passenger.getTelephone());

		return passengerDto;
    }

	public List<PassengerDto> mapToPassengersDto(List<Passenger> passengers) {
		return passengers.stream().map(this::mapToPassengerDto).collect(Collectors.toList());
	}
}
