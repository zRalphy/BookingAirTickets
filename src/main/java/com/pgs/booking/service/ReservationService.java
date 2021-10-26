package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdatePassengerDtoMapper;
import com.pgs.booking.mappers.ReservationDtoMapper;
import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.Flight;
import com.pgs.booking.model.entity.Reservation;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final ReservationDtoMapper reservationDtoMapper;
    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper;

    public ReservationDto getReservationWithPassengersAndFlight(long id) {
        Reservation reservationById = reservationRepository.findReservationById(id);
        return reservationDtoMapper.mapToReservationDto(reservationById);
    }

    public List<ReservationDto> getReservationsByFlight(long id) {
        List<Reservation> reservationsByFlight = reservationRepository.findAllByFlightId(id);
        return reservationDtoMapper.mapToReservationsDto(reservationsByFlight);
    }

    public List<ReservationDto> getReservationsByCurrentUser(User user) {
        List<Reservation> reservationsByCurrentUser = reservationRepository.findAllByUserId(user.getId());
        return reservationDtoMapper.mapToReservationsDto(reservationsByCurrentUser);
    }

    public List<ReservationDto> getReservationsByUser(long id) {
        List<Reservation> reservationsByUser = reservationRepository.findAllByUserId(id);
        return reservationDtoMapper.mapToReservationsDto(reservationsByUser);
    }

    public ReservationDto addReservation(CreateUpdateReservationDto createUpdateReservationDto, User user) {
        Optional<Flight> flight = flightRepository.findById(createUpdateReservationDto.getFlightId());
        if (flight.isEmpty()) {
            throw new ResourceNotFoundException("Flight with this id not exist in database.");
        }
        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.ReservationStatus.IN_PROGRESS);
        reservation.setFlight(flight.get());
        reservation.setUser(user);
        reservation.setPassengers(createUpdatePassengerDtoMapper.mapToPassengers(createUpdateReservationDto.getPassengers()));
        Reservation reservationToSave = reservationRepository.save(reservation);
        return reservationDtoMapper.mapToReservationDto(reservationToSave);
    }

    public void deleteReservation(long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation with id " + id + " does not exists.");
        }
        reservationRepository.deleteById(id);
    }

    @Transactional
    public ReservationDto realizedReservation(long id) {
        return setReservationStatus(id, Reservation.ReservationStatus.REALIZED);
    }

    @Transactional
    public ReservationDto canceledReservation(long id) {
        return setReservationStatus(id, Reservation.ReservationStatus.CANCELED);
    }

    private Reservation getReservationById(long id) {
        return reservationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Reservation with id " + id + "not found"));
    }

    private ReservationDto setReservationStatus(long id, Reservation.ReservationStatus status) {
        var reservation = getReservationById(id);
        reservation.setStatus(status);
        var reservationToSave = reservationRepository.save(reservation);
        return reservationDtoMapper.mapToReservationDto(reservationToSave);
    }
}

