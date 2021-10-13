package com.pgs.booking.controller;

import com.pgs.booking.mappers.PassengerDtoMapper;
import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.dto.PassengerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class PassengerDtoMapperTest {

    private final PassengerDtoMapper passengerDtoMapper = new PassengerDtoMapper();
    private List<Passenger> passengerList = new ArrayList<>();
    private List<PassengerDto> passengerDtoList = new ArrayList<>();

    @Test
    @DisplayName("Mapper passenger list test successful")
    void mapToPassengersDto() {

        Passenger passenger1 = new Passenger();
        passenger1.setFirstName("Tomasz");
        passenger1.setLastName("Nowak");
        passenger1.setEmail("tomasznowak@gmail.com");
        passenger1.setCountry("France");
        passenger1.setTelephone("123456789");

        Passenger passenger2 = new Passenger();
        passenger1.setFirstName("Ala");
        passenger1.setLastName("Kot");
        passenger1.setEmail("alakot@gmail.com");
        passenger1.setCountry("USA");
        passenger1.setTelephone("222456789");

        passengerList = Arrays.asList(passenger1, passenger2);
        passengerDtoList = passengerDtoMapper.mapToPassengersDto(passengerList);

        assertAll("Passenger",
                () -> assertThat(passengerDtoList).extracting(PassengerDto::getFirstName)
                        .doesNotContain("Adam", "Karol"),
                () -> assertThat(passengerList)
                        .isNotEmpty()
                        .hasSize(2)
                        .contains(passenger1, passenger2),
                () -> assertThat(passengerDtoList).extracting( "firstName", "lastName", "email", "country", "telephone")
                        .containsAnyOf(tuple("Tomasz", "Nowak", "tomasznowak@gmail.com", "France", "123456789"),
                                tuple("Ala", "Kot", "alakot@gmail.com", "USA", "222456789")));
    }

    @Test
    @DisplayName("Mapper passenger test successful")
    void mapToPassengerDto() {

        Passenger passenger3 = new Passenger();
        passenger3.setFirstName("Tom");
        passenger3.setLastName("Tom");
        passenger3.setEmail("tomtom@gmail.com");
        passenger3.setCountry("France");
        passenger3.setTelephone("123456789");

       var passengerDto = passengerDtoMapper.mapToPassengerDto(passenger3);

        assertAll("Passenger",
                () -> assumeTrue(passenger3.getFirstName().equals(passengerDto.getFirstName())),
                () -> assumeTrue(passenger3.getLastName().equals(passengerDto.getLastName())),
                () -> assumeTrue(passenger3.getEmail().equals(passengerDto.getEmail())),
                () -> assumeTrue(passenger3.getCountry().equals(passengerDto.getCountry())),
                () -> assumeTrue(passenger3.getTelephone().equals(passengerDto.getTelephone()))
        );
    }
}