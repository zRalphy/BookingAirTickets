package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdatePassengerDtoMapper;
import com.pgs.booking.mappers.ReservationDtoMapper;
import com.pgs.booking.model.Flight;
import com.pgs.booking.model.Reservation;
import com.pgs.booking.model.User;
import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.ReservationRepository;
import com.pgs.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final ReservationDtoMapper reservationDtoMapper;
    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper;

    public List<ReservationDto> getReservationByFlight(long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        if(flight.isEmpty()){
            throw new ResourceNotFoundException("Flight with this id not exist in database.");
        }
        List<Reservation> allReservation = reservationRepository.findAll(Sort.by(Sort.Order.asc("id")));
        List<Long> ids = allReservation.stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
        List<Reservation> reservations = reservationRepository.findAllByFlightIdIn(ids);
        return reservationDtoMapper.mapToReservationsDto(reservations);
    }

    @Transient
    //NOT IMPLEMENTED YET
    public List<ReservationDto> getReservationByUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User with this id not exist in database.");
        }
        List<Reservation> allReservation = reservationRepository.findAll(Sort.by(Sort.Order.asc("id")));
        List<Long> ids = allReservation.stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
        List<Reservation> reservations = reservationRepository.findAllByFlightIdIn(ids);
        return reservationDtoMapper.mapToReservationsDto(reservations);
    }

    public ReservationDto addReservation(CreateUpdateReservationDto createUpdateReservationDto) {
        Optional<Flight> flight = flightRepository.findById(createUpdateReservationDto.getFlightId());
        if(flight.isEmpty()){
            throw new ResourceNotFoundException("Flight with this id not exist in database.");
        }
        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.ReservationStatus.IN_PROGRESS);
        reservation.setFlight(flight.get());
        reservation.setPassengers(createUpdatePassengerDtoMapper.mapToPassengers(createUpdateReservationDto.getPassengers()));
        Reservation reservationToSave = reservationRepository.save(reservation);
        return reservationDtoMapper.mapToReservationDto(reservationToSave);
    }

    public ReservationDto realizedReservation(long id){
        return setReservationDtoStatus(id, Reservation.ReservationStatus.REALIZED);
    }

    public ReservationDto canceledReservation(long id){
        return setReservationDtoStatus(id, Reservation.ReservationStatus.CANCELED);
    }

    private Reservation getReservationById(long id) {
        return reservationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Reservation with id " + id + "not found"));
    }

    private ReservationDto setReservationDtoStatus(long id, Reservation.ReservationStatus status) {
        var reservation = getReservationById(id);
        reservation.setStatus(status);
        var reservationToSave = reservationRepository.save(reservation);
        return reservationDtoMapper.mapToReservationDto(reservationToSave);
    }
}

