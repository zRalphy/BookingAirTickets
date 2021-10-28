package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.*;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.entity.*;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FlightRepository flightRepository;

    private final PassengerDtoMapper passengerDtoMapper = new PassengerDtoMapper();
    private final ReservationDtoMapper reservationDtoMapper = new ReservationDtoMapper(passengerDtoMapper);
    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper = new CreateUpdatePassengerDtoMapper();
    private ReservationService testReservationService;

    @BeforeEach
    void setUp() {
        testReservationService = new ReservationService(reservationRepository, flightRepository, reservationDtoMapper, createUpdatePassengerDtoMapper);
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

    private static final User USER_1 = User.builder()
            .id(1L)
            .username("user")
            .password("user")
            .build();

    private static final Passenger PASSENGER = Passenger.builder()
            .id(1L)
            .firstName("Tommy")
            .lastName("Hilfiger")
            .email("HilfigerTommy@gmail.com")
            .country("USA")
            .telephone("235689123")
            .build();

    private static final CreateUpdatePassengerDto CREATE_UPDATE_PASSENGER_DTO = CreateUpdatePassengerDto.builder()
            .firstName("Dwayne")
            .lastName("Johnson")
            .email("JohnsonDwayne@gmail.com")
            .country("USA")
            .telephone("123456789")
            .build();

    private static final Flight FLIGHT = Flight.builder()
            .id(1L)
            .type(Flight.TypeOfFlight.ECONOMY)
            .departureDate(LocalDateTime.of(2021, Month.DECEMBER, 9, 18, 30))
            .arrivalDate(LocalDateTime.of(2021, Month.DECEMBER, 9, 23, 30))
            .departureAirport(AIRPORT1)
            .arrivalAirport(AIRPORT2)
            .build();

    private static final Reservation RESERVATION = Reservation.builder()
            .id(1L)
            .flight(FLIGHT)
            .user(USER_1)
            .passengers(List.of(PASSENGER))
            .status(Reservation.ReservationStatus.IN_PROGRESS)
            .build();

    private static final CreateUpdateReservationDto CREATE_UPDATE_RESERVATION_DTO = CreateUpdateReservationDto.builder()
            .flightId(1L)
            .passengers(List.of(CREATE_UPDATE_PASSENGER_DTO))
            .build();

    @Test
    void getReservationByFlight() {
        //given
        long id = 1L;
        given(reservationRepository.findAllByFlightId(id))
                .willReturn(Collections.singletonList(RESERVATION));
        //when
        var reservationsByFlight = testReservationService.getReservationsByFlight(id);
        var reservationByFlight = reservationsByFlight.get(0);
        //then
        assertEquals(RESERVATION.getId(), reservationByFlight.getId().longValue());
        assertEquals(RESERVATION.getFlight().getId(), reservationByFlight.getFlightId());
        assertEquals(RESERVATION.getPassengers().get(0).getId(), reservationByFlight.getPassengers().get(0).getId().longValue());
        assertEquals(RESERVATION.getPassengers().get(0).getFirstName(), reservationByFlight.getPassengers().get(0).getFirstName());
        assertEquals(RESERVATION.getPassengers().get(0).getLastName(), reservationByFlight.getPassengers().get(0).getLastName());
        assertEquals(RESERVATION.getStatus().toString(), reservationByFlight.getStatus().toString());
        verify(reservationRepository).findAllByFlightId(id);
    }

    @Test
    void getReservationByUser() {
        //given
        long id = 1L;
        given(reservationRepository.findAllByUserId(id))
                .willReturn(Collections.singletonList(RESERVATION));
        //when
        var reservationsByUser = testReservationService.getReservationsByUser(id);
        var reservationByUser = reservationsByUser.get(0);
        //then
        assertEquals(RESERVATION.getId(), reservationByUser.getId().longValue());
        assertEquals(RESERVATION.getFlight().getId(), reservationByUser.getFlightId());
        assertEquals(RESERVATION.getPassengers().get(0).getId(), reservationByUser.getPassengers().get(0).getId().longValue());
        assertEquals(RESERVATION.getPassengers().get(0).getFirstName(), reservationByUser.getPassengers().get(0).getFirstName());
        assertEquals(RESERVATION.getPassengers().get(0).getLastName(), reservationByUser.getPassengers().get(0).getLastName());
        assertEquals(RESERVATION.getStatus().toString(), reservationByUser.getStatus().toString());
        verify(reservationRepository).findAllByUserId(id);
    }

    @Test
    void addReservation() {
        //given
        given(reservationRepository.save(any(Reservation.class))).willReturn(RESERVATION);
        given(flightRepository.findById(1L)).willReturn(Optional.ofNullable(FLIGHT));
        //when
        var reservation = testReservationService.addReservation(CREATE_UPDATE_RESERVATION_DTO, USER_1);
        //then
        assertEquals(reservation.getId(), RESERVATION.getId());
        assertEquals(reservation.getFlightId(), RESERVATION.getFlight().getId());
        assertEquals(reservation.getUserId(), RESERVATION.getUser().getId());
        assertEquals(reservation.getPassengers().get(0).getFirstName(), RESERVATION.getPassengers().get(0).getFirstName());
        assertEquals(reservation.getPassengers().get(0).getLastName(), RESERVATION.getPassengers().get(0).getLastName());
        assertEquals(reservation.getStatus(), RESERVATION.getStatus());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testDeleteReservationIfIdExist() {
        //given
        long id = 1L;
        given(reservationRepository.existsById(id)).willReturn(Boolean.TRUE);
        doNothing().when(reservationRepository).deleteById(id);
        //when
        testReservationService.deleteReservation(id);
        //then
        verify(reservationRepository).deleteById(id);
    }

    @Test
    void testDeleteReservationIfIdNotExist() {
        //given
        long id = 1L;
        given(reservationRepository.existsById(id)).willReturn(Boolean.FALSE);
        //when
        assertThrows(ResourceNotFoundException.class, () -> testReservationService
                .deleteReservation(id));
        //then
        verify(reservationRepository, times(0)).deleteById(id);
    }

    @Test
    void testRealizedReservationIfIdExist() {
        //given
        long id = 1L;
        //given
        given(reservationRepository.findById(id))
                .willReturn(java.util.Optional.ofNullable(RESERVATION));
        given(reservationRepository.save(any(Reservation.class))).willReturn(RESERVATION);
        //when
        var reservation = testReservationService.realizedReservation(id);
        //then
        assertEquals("REALIZED", reservation.getStatus().toString());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testRealizedReservationIfIdNotExist() {
        //given
        long id = 1L;

        given(reservationRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        assertThrows(ResourceNotFoundException.class, () -> testReservationService
                .realizedReservation(id));
        //then
        verify(reservationRepository, times(0)).save(any(Reservation.class));
    }

    @Test
    void testCanceledReservationIfIdExist() {
        //given
        long id = 1L;
        //given
        given(reservationRepository.findById(id))
                .willReturn(java.util.Optional.ofNullable(RESERVATION));
        given(reservationRepository.save(any(Reservation.class))).willReturn(RESERVATION);
        //when
        var reservation = testReservationService.canceledReservation(id);
        //then
        assertEquals("CANCELED", reservation.getStatus().toString());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testCanceledReservationIfIdNotExist() {
        //given
        long id = 1L;
        given(reservationRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        assertThrows(ResourceNotFoundException.class, () -> testReservationService
                .canceledReservation(id));
        //then
        verify(reservationRepository, times(0)).save(any(Reservation.class));
    }
}