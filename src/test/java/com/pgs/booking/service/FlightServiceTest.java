package com.pgs.booking.service;

import com.pgs.booking.mappers.CreateUpdateFlightDtoMapper;
import com.pgs.booking.mappers.FlightDtoMapper;
import com.pgs.booking.model.Flight;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.repository.FlightRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    private final FlightDtoMapper flightDtoMapper = new FlightDtoMapper();
    private final CreateUpdateFlightDtoMapper createUpdateFlightDtoMapper = new CreateUpdateFlightDtoMapper();

    @InjectMocks
    private FlightService underTest;

    @BeforeEach
    void setUp() {
        underTest = new FlightService(flightRepository, flightDtoMapper, createUpdateFlightDtoMapper);

        when(flightRepository.findAll())
                .thenReturn(List.of(new Flight(), new Flight(), new Flight(), new Flight()));
    }

    private static Flight FLIGHT = Flight.builder()
            .type(Flight.TypeOfFlight.ECONOMY)
            .departureDate(LocalDateTime.now())
            .arrivalDate(LocalDateTime.now())
            .build();

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
        var flights = underTest.getFlights();
        //then
        Assertions.assertEquals(4, flights.size());
        verify(flightRepository).findAll();
    }

    @Test
    void testGetFlight() {
        //given
        long id = 1L;
        when(flightRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(FLIGHT));
        //when
        var flight = underTest.getFlight(id);
        //then
        Assertions.assertEquals(id, flight.getId());
        verify(flightRepository).findById(id);
    }

    @Test
    void testAddFlight() {

        when(flightRepository.save(createUpdateFlightDtoMapper.mapToFlight(CREATE_UPDATE_FLIGHT_DTO))).thenAnswer(invocationOnMock -> {
            Flight f = invocationOnMock.getArgument(0);
            f.setId(1L);
            return f;
        });

        //when
        underTest.addFlight(CREATE_UPDATE_FLIGHT_DTO);

        //then
        ArgumentCaptor<Flight> flightArgumentCaptor = ArgumentCaptor.forClass(Flight.class);
        verify(flightRepository).save(flightArgumentCaptor.capture());

        Flight capturedFlight = flightArgumentCaptor.getValue();
        assertThat(capturedFlight).isEqualTo(CREATE_UPDATE_FLIGHT_DTO.getType());
        assertThat(capturedFlight).isEqualTo(CREATE_UPDATE_FLIGHT_DTO.getDepartureDate());
        assertThat(capturedFlight).isEqualTo(CREATE_UPDATE_FLIGHT_DTO.getArrivalDate());
    }

    @Test
    void testEditFlight() {
    }

    @Test
    void testDeleteFlight() {
        //given
        long id = 1L;
        //when
        var flight = underTest.getFlight(id);
        underTest.deleteFlight(id);
        //then
        Assertions.assertEquals(null, flight.getId());
    }


}