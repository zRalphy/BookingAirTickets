package com.pgs.booking.service;

import com.pgs.booking.model.Flight;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;
    @Mock
    private FlightService underTest;

    private static FlightDto FLIGHT_DTO = FlightDto.builder()
            .id(1L)
            .type(Flight.TypeOfFlight.ECONOMY)
            .departureDate(LocalDateTime.now())
            .arrivalDate(LocalDateTime.now())
            .build();

    private static CreateUpdateFlightDto CREATE_UPDATE_FLIGHT_DTO = CreateUpdateFlightDto.builder()
            .type(Flight.TypeOfFlight.BUSINESS)
            .departureDate(LocalDateTime.now())
            .arrivalDate(LocalDateTime.now())
            .build();

    @Test
    void testGetFlights() {
        //when
        underTest.getFlights();
        //then
        verify(flightRepository).findAll();
    }

    @Test
    void testGetFlight() {
        long id = 1L;
        //when
        underTest.getFlight(id);
        //then
        verify(flightRepository).findById(id);
    }

    @Test
    void testAddFlight() {
        //when
        underTest.addFlight(CREATE_UPDATE_FLIGHT_DTO);
        //then
        ArgumentCaptor<Flight> flightArgumentCaptor = ArgumentCaptor.forClass(Flight.class);
        verify(flightRepository).save(flightArgumentCaptor.capture());

        Flight capturedFlight = flightArgumentCaptor.getValue();
        assertThat(capturedFlight).isEqualTo(CREATE_UPDATE_FLIGHT_DTO);
    }

    @Test
    void testEditFlight() {
    }

    @Test
    void testDeleteFlight() {
    }
}