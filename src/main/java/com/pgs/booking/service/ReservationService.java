package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdatePassengerDtoMapper;
import com.pgs.booking.mappers.PassengerDtoMapper;
import com.pgs.booking.mappers.ReservationDtoMapper;
import com.pgs.booking.model.Reservation;
import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.PassengerRepository;
import com.pgs.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;
    private final PassengerRepository passengerRepository;
    private final ReservationDtoMapper reservationDtoMapper;
    private final CreateUpdateReservationDto createUpdateReservationDto;
    private final PassengerService passengerService;
    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper;
    private final PassengerDtoMapper passengerDtoMapper;

    public List<ReservationDto> getReservations() {
        return null;
    }

    public ReservationDto getReservation(long id) {
        return null;
    }

    public ReservationDto addReservation(CreateUpdateReservationDto createUpdateReservationDto) {

        var flight_id = createUpdateReservationDto.getFlight_id();
        var passengersDtoList = passengerService.getPassengers();
        //var passengersList = createUpdatePassengerDtoMapper.mapToPassengers(passengersDtoList);

        Reservation reservation = new Reservation();
        //reservation.setStatus();
        //reservation.setFlight_id();
        //reservation.setPassengers();
        reservationRepository.save(reservation);
        return reservationDtoMapper.mapToReservationDto(reservation);
    }

    public Reservation.ReservationStatus setReservationStatusCanceled(Reservation.ReservationStatus reservationStatus) {
        return reservationStatus;
    }

    public Reservation.ReservationStatus setReservationStatusRealized(Reservation.ReservationStatus reservationStatus) {
        return reservationStatus;
    }

    public void deleteReservation(long id) {
        if(!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation with id " + id + " not found.");
        }
        reservationRepository.deleteById(id);
    }
}
