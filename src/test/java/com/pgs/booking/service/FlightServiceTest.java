package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.AirportDtoMapper;
import com.pgs.booking.mappers.CreateUpdateFlightDtoMapper;
import com.pgs.booking.mappers.FlightDtoMapper;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.entity.Airport;
import com.pgs.booking.model.entity.Flight;
import com.pgs.booking.repository.AirportRepository;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private AirportRepository airportRepository;

    private final AirportDtoMapper airportDtoMapper = new AirportDtoMapper();
    private final FlightDtoMapper flightDtoMapper = new FlightDtoMapper(airportDtoMapper);
    private final CreateUpdateFlightDtoMapper createUpdateFlightDtoMapper = new CreateUpdateFlightDtoMapper();
    private FlightService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FlightService(flightRepository, flightDtoMapper, createUpdateFlightDtoMapper, reservationRepository, airportRepository);
    }

    private static final Airport AIRPORT1 = Airport.builder()
            .id(1L)
            .code("PSS")
            .country("POLAND")
            .name("Wroclaw Airport")
            .build();

    private static final Airport AIRPORT2 = Airport.builder()
            .id(2L)
            .code("BMM")
            .country("FRANCE")
            .name("US Airport")
            .build();

    private static Flight FLIGHT = Flight.builder()
            .id(1L)
            .type(Flight.TypeOfFlight.ECONOMY)
            .departureDate(LocalDateTime.of(2021, Month.DECEMBER, 9, 18, 30))
            .arrivalDate(LocalDateTime.of(2021, Month.DECEMBER, 9, 23, 30))
            .departureAirport(AIRPORT1)
            .arrivalAirport(AIRPORT2)
            .build();

    private static final CreateUpdateFlightDto CREATE_UPDATE_FLIGHT_DTO = CreateUpdateFlightDto.builder()
            .type(Flight.TypeOfFlight.BUSINESS)
            .departureDate(LocalDateTime.of(2021, Month.SEPTEMBER, 10, 8, 30))
            .arrivalDate(LocalDateTime.of(2021, Month.SEPTEMBER, 10, 11, 30))
            .arrivalAirportIataCode("PSS")
            .arrivalAirportIataCode("CCD")
            .build();

    @Test
    void testGetFlights() {
        given(flightRepository.findAll())
                .willReturn(List.of(FLIGHT, FLIGHT, FLIGHT, FLIGHT));
        //when
        var flights = underTest.getFlights();
        //then
        assertEquals(4, flights.size());
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
        assertEquals(FLIGHT.getType(), flight.getType());
        assertEquals(FLIGHT.getDepartureDate(), flight.getDepartureDate());
        assertEquals(FLIGHT.getArrivalDate(), flight.getArrivalDate());
        verify(flightRepository).findById(id);
    }

    @Test
    void testAddFlight() {
        //given
        given(flightRepository.save(any(Flight.class))).willReturn(FLIGHT);
        given(airportRepository.findAirportByCode_Opt(any(String.class))).willReturn(Optional.ofNullable(AIRPORT1));
        //when
        var flight = underTest.addFlight(CREATE_UPDATE_FLIGHT_DTO);
        //then
        assertEquals(flight.getId(), FLIGHT.getId());
        assertEquals(flight.getType(), FLIGHT.getType());
        assertEquals(flight.getDepartureDate(), FLIGHT.getDepartureDate());
        assertEquals(flight.getArrivalDate(), FLIGHT.getArrivalDate());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void testEditFlightIfIdExist() {
        long id = 1L;
        //given
        given(flightRepository.findById(id))
                .willReturn(Optional.ofNullable(FLIGHT));
        given(airportRepository.findAirportByCode_Opt(any(String.class))).willReturn(Optional.empty());
        given(flightRepository.save(any(Flight.class))).willReturn(FLIGHT);
        //when
        var flight = underTest.editFlight(id, CREATE_UPDATE_FLIGHT_DTO);
        //then
        assertEquals(flight.getType(), FLIGHT.getType());
        assertEquals(flight.getDepartureDate(), FLIGHT.getDepartureDate());
        assertEquals(flight.getArrivalDate(), FLIGHT.getArrivalDate());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void testEditFlightIfIdNotExist() {
        long id = 1L;
        //given
        given(flightRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        assertThrows(ResourceNotFoundException.class, () -> underTest
                .editFlight(id, CREATE_UPDATE_FLIGHT_DTO));
        //then
        verify(flightRepository, times(0)).save(any(Flight.class));
    }

    @Test
    void testDeleteFlightIfIdExist() {
        //given
        long id = 1L;
        given(flightRepository.existsById(id)).willReturn(Boolean.TRUE);
        doNothing().when(flightRepository).deleteById(id);
        //when
        underTest.deleteFlight(id);
        //then
        verify(flightRepository).deleteById(id);
    }

    @Test
    void testDeleteFlightIfIdNotExist() {
        //given
        long id = 1L;
        given(flightRepository.existsById(id)).willReturn(Boolean.FALSE);
        //when
        assertThrows(ResourceNotFoundException.class, () -> underTest
                .deleteFlight(id));
        //then
        verify(flightRepository, times(0)).deleteById(id);
    }
}
