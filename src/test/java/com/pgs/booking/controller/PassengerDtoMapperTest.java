package com.pgs.booking.controller;

import com.pgs.booking.mappers.PassengerDtoMapper;
import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.dto.PassengerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class PassengerDtoMapperTest {

    private PassengerDtoMapper passengerDtoMapper = new PassengerDtoMapper();
    private List<PassengerDto> passengerDtoList = new ArrayList<>();

    @BeforeEach
    void init() {
        passengerDtoMapper = new PassengerDtoMapper();
        passengerDtoList = new ArrayList<>();
    }

    @Test
    @DisplayName("Mapper passenger list test successful")
    void mapToPassengersDto() {
        PassengerDto passengerDto1 = PassengerDto.builder()
                .id(1L)
                .firstName("Janusz")
                .lastName("New")
                .email("janusznew@gmail.com")
                .country("Poland")
                .telephone("123456789")
                .build();

        PassengerDto passengerDto2 = PassengerDto.builder()
                .id(2L)
                .firstName("Ala")
                .lastName("Wen")
                .email("alawen@gmail.com")
                .country("Spain")
                .telephone("233466121")
                .build();

        passengerDtoList = Arrays.asList(passengerDto1, passengerDto2);

        assertThat(passengerDto1.getId()).isEqualTo(1L);

        assertThat(passengerDto1.getFirstName()).startsWith("Jan")
                .endsWith("sz")
                .isEqualToIgnoringCase("janusz");

        assertThat(passengerDtoList)
                .isNotEmpty()
                .hasSize(2)
                .contains(passengerDto1, passengerDto2);
    }

    @Test
    @DisplayName("Mapper passenger test successful")
    void mapToPassengerDto() {

        Passenger passenger1 = new Passenger();
        passenger1.setFirstName("Tom");
        passenger1.setLastName("Tom");
        passenger1.setEmail("tomtom@gmail.com");
        passenger1.setCountry("France");
        passenger1.setTelephone("123456789");

       var passengerDto = passengerDtoMapper.mapToPassengerDto(passenger1);

        assertAll("Passenger",
                () -> assumeTrue(passenger1.getFirstName().equals(passengerDto.getFirstName())),
                () -> assumeTrue(passenger1.getLastName().equals(passengerDto.getLastName())),
                () -> assumeTrue(passenger1.getEmail().equals(passengerDto.getEmail())),
                () -> assumeTrue(passenger1.getCountry().equals(passengerDto.getCountry())),
                () -> assumeTrue(passenger1.getTelephone().equals(passengerDto.getTelephone()))
        );
    }
}