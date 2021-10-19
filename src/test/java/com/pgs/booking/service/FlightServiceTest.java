package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.dto.CreateUpdateFlightDtoMapper;
import com.pgs.booking.mappers.dto.FlightDtoMapper;
import com.pgs.booking.model.Flight;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.repository.FlightRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    private final FlightDtoMapper flightDtoMapper = new FlightDtoMapper();
    private final CreateUpdateFlightDtoMapper createUpdateFlightDtoMapper = new CreateUpdateFlightDtoMapper();
    private FlightService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FlightService(flightRepository, flightDtoMapper, createUpdateFlightDtoMapper);
    }

    private static Flight FLIGHT = Flight.builder()
            .id(1L)
            .type(Flight.TypeOfFlight.ECONOMY)
            .departureDate(LocalDateTime.of(2021, Month.DECEMBER, 9, 18, 30))
            .arrivalDate(LocalDateTime.of(2021, Month.DECEMBER, 9, 23, 30))
            .build();

    private static CreateUpdateFlightDto CREATE_UPDATE_FLIGHT_DTO = CreateUpdateFlightDto.builder()
            .type(Flight.TypeOfFlight.BUSINESS)
            .departureDate(LocalDateTime.of(2021, Month.SEPTEMBER, 10, 8, 30))
            .arrivalDate(LocalDateTime.of(2021, Month.SEPTEMBER, 10, 11, 30))
            .build();

    @Test
    void testGetFlights() {
        given(flightRepository.findAll())
                .willReturn(List.of(new Flight(), new Flight(), new Flight(), new Flight()));
        //when
        var flights = underTest.getFlights();
        //then
        Assertions.assertEquals(4, flights.size());
        verify(flightRepository).findAll();
    }

    @Test
    void testGetFlight() {
        //given
        long id = 1L;
        given(flightRepository.findById(id))
                .willReturn(java.util.Optional.ofNullable(FLIGHT));
        //when
        var flight = underTest.getFlight(id);
        //then
        Assertions.assertEquals(FLIGHT.getType(), flight.getType());
        Assertions.assertEquals(FLIGHT.getDepartureDate(), flight.getDepartureDate());
        Assertions.assertEquals(FLIGHT.getArrivalDate(), flight.getArrivalDate());
        verify(flightRepository).findById(id);
    }

    @Test
    void testAddFlight() {
        //given
        given(flightRepository.save(any(Flight.class))).willReturn(FLIGHT);
        //when
        var flight = underTest.addFlight(CREATE_UPDATE_FLIGHT_DTO);
        //then
        Assertions.assertEquals(flight.getId(), FLIGHT.getId());
        Assertions.assertEquals(flight.getType(), FLIGHT.getType());
        Assertions.assertEquals(flight.getDepartureDate(), FLIGHT.getDepartureDate());
        Assertions.assertEquals(flight.getArrivalDate(), FLIGHT.getArrivalDate());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void testEditFlightIfIdExist() {
        long id = 1L;
        //given
        given(flightRepository.findById(id))
                .willReturn(java.util.Optional.ofNullable(FLIGHT));
        given(flightRepository.save(any(Flight.class))).willReturn(FLIGHT);
        //when
        var flight = underTest.editFlight(id, CREATE_UPDATE_FLIGHT_DTO);
        //then
        Assertions.assertEquals(flight.getType(), FLIGHT.getType());
        Assertions.assertEquals(flight.getDepartureDate(), FLIGHT.getDepartureDate());
        Assertions.assertEquals(flight.getArrivalDate(), FLIGHT.getArrivalDate());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void testEditFlightIfIdNotExist() {
        long id = 1L;
        //given
        given(flightRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        Assertions.assertThrows(ResourceNotFoundException.class, () -> underTest
                .editFlight(id, CREATE_UPDATE_FLIGHT_DTO));
        //then
        Mockito.verify(flightRepository, times(0)).save(any(Flight.class));
    }

    @Test
    void testDeleteFlightIfIdExist() {
        //given
        long id = 1L;
        given(flightRepository.existsById(id)).willReturn(Boolean.TRUE);
        Mockito.doNothing().when(flightRepository).deleteById(id);
        //when
        underTest.deleteFlight(id);
        //then
        Mockito.verify(flightRepository).deleteById(id);
    }

    @Test
    void testDeleteFlightIfIdNotExist() {
        //given
        long id = 1L;
        given(flightRepository.existsById(id)).willReturn(Boolean.FALSE);
        //when
        Assertions.assertThrows(ResourceNotFoundException.class, () -> underTest
                .deleteFlight(id));
        //then
        Mockito.verify(flightRepository, times(0)).deleteById(id);
    }
}
