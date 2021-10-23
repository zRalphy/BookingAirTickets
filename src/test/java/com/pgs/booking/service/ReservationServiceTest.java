package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdatePassengerDtoMapper;
import com.pgs.booking.mappers.FlightDtoMapper;
import com.pgs.booking.mappers.PassengerDtoMapper;
import com.pgs.booking.mappers.ReservationDtoMapper;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.*;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.ReservationRepository;
import com.pgs.booking.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private UserRepository userRepository;

    private final FlightDtoMapper flightDtoMapper = new FlightDtoMapper();
    private final PassengerDtoMapper passengerDtoMapper = new PassengerDtoMapper();
    private final ReservationDtoMapper reservationDtoMapper = new ReservationDtoMapper(passengerDtoMapper, flightDtoMapper);
    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper = new CreateUpdatePassengerDtoMapper();
    private ReservationService testReservationService;

    @BeforeEach
    void setUp() {
        testReservationService = new ReservationService(reservationRepository, flightRepository, userRepository, reservationDtoMapper, createUpdatePassengerDtoMapper);
    }

    private static final Role ROLE_1 = Role.builder()
            .name("USER")
            .build();

    private static final List<Role> ROLE_LIST = List.of(ROLE_1);

    private static final User USER_1 = User.builder()
            .id(1L)
            .username("user1")
            .roles(ROLE_LIST)
            .build();

    private static final Passenger PASSENGER = Passenger.builder()
            .id(1L)
            .firstName("Tommy")
            .lastName("Hilfiger")
            .email("HilfigerTommy@gmail.com")
            .country("USA")
            .telephone("235689123")
            .build();

    private static final PassengerDto PASSENGER_DTO = PassengerDto.builder()
            .id(1L)
            .firstName("Johny")
            .lastName("Deep")
            .email("DeepJohn@gmail.com")
            .country("USA")
            .telephone("234666788")
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
            .build();

    private static final Reservation RESERVATION = Reservation.builder()
            .id(1L)
            .flight(FLIGHT)
            .user(USER_1)
            .passengers(List.of(PASSENGER))
            .status(Reservation.ReservationStatus.IN_PROGRESS)
            .build();

    private static final ReservationDto RESERVATION_DTO = ReservationDto.builder()
            .id(1L)
            .flightId(1L)
            .userId(1L)
            .passengers(List.of(PASSENGER_DTO))
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
        var reservationsByFlight = testReservationService.getReservationByFlight(id);
        var reservationByFlight = reservationsByFlight.get(0);

        //then
        Assertions.assertEquals(RESERVATION.getId(), reservationByFlight.getId().longValue());
        Assertions.assertEquals(RESERVATION.getFlight().getId(), reservationByFlight.getFlightId());
        Assertions.assertEquals(RESERVATION.getPassengers().get(0).getId(), reservationByFlight.getPassengers().get(0).getId().longValue());
        Assertions.assertEquals(RESERVATION.getPassengers().get(0).getFirstName(), reservationByFlight.getPassengers().get(0).getFirstName());
        Assertions.assertEquals(RESERVATION.getPassengers().get(0).getLastName(), reservationByFlight.getPassengers().get(0).getLastName());
        Assertions.assertEquals(RESERVATION.getStatus().toString(), reservationByFlight.getStatus().toString());
        verify(reservationRepository).findAllByFlightId(id);
    }

    @Test
    void getReservationByUser() {
        //given
        long id = 1L;
        given(reservationRepository.findAllByUserId(id))
                .willReturn(Collections.singletonList(RESERVATION));
        //when
        var reservationsByUser = testReservationService.getReservationByUser(id);
        var reservationByUser = reservationsByUser.get(0);

        //then
        Assertions.assertEquals(RESERVATION.getId(), reservationByUser.getId().longValue());
        Assertions.assertEquals(RESERVATION.getFlight().getId(), reservationByUser.getFlightId());
        Assertions.assertEquals(RESERVATION.getPassengers().get(0).getId(), reservationByUser.getPassengers().get(0).getId().longValue());
        Assertions.assertEquals(RESERVATION.getPassengers().get(0).getFirstName(), reservationByUser.getPassengers().get(0).getFirstName());
        Assertions.assertEquals(RESERVATION.getPassengers().get(0).getLastName(), reservationByUser.getPassengers().get(0).getLastName());
        Assertions.assertEquals(RESERVATION.getStatus().toString(), reservationByUser.getStatus().toString());
        verify(reservationRepository).findAllByUserId(id);
    }

    @Test
    void addReservation() {
        //given
        given(reservationRepository.save(any(Reservation.class))).willReturn(RESERVATION);
        //when
        var reservation = testReservationService.addReservation(CREATE_UPDATE_RESERVATION_DTO, USER_1);
        //then
        Assertions.assertEquals(reservation.getId(), RESERVATION.getId());
        Assertions.assertEquals(reservation.getFlightId(), RESERVATION.getFlight().getId());
        Assertions.assertEquals(reservation.getUserId(), USER_1.getId());
        Assertions.assertEquals(reservation.getPassengers().get(0).getFirstName(), RESERVATION.getPassengers().get(0).getFirstName());
        Assertions.assertEquals(reservation.getPassengers().get(0).getLastName(), RESERVATION.getPassengers().get(0).getLastName());
        Assertions.assertEquals(reservation.getStatus(), RESERVATION.getStatus());
        verify(reservationRepository).save(any(Reservation.class));
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
        Assertions.assertEquals("REALIZED", reservation.getStatus().toString());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testRealizedReservationIfIdNotExist() {
        //given
        long id = 1L;

        given(reservationRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        Assertions.assertThrows(ResourceNotFoundException.class, () -> testReservationService
                .realizedReservation(id));
        //then
        Mockito.verify(reservationRepository, times(0)).save(any(Reservation.class));
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
        Assertions.assertEquals("CANCELED", reservation.getStatus().toString());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void testCanceledReservationIfIdNotExist() {
        //given
        long id = 1L;

        given(reservationRepository.findById(id))
                .willReturn(Optional.empty());
        //when
        Assertions.assertThrows(ResourceNotFoundException.class, () -> testReservationService
                .canceledReservation(id));
        //then
        Mockito.verify(reservationRepository, times(0)).save(any(Reservation.class));
    }
}