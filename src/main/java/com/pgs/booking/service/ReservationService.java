package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdateFlightDtoMapper;
import com.pgs.booking.mappers.FlightDtoMapper;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
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
    private final FlightDtoMapper flightDtoMapper;
    private final CreateUpdateFlightDtoMapper createUpdateFlightDtoMapper;

    public List<ReservationDto> getReservations() {
        return null;
    }

    public ReservationDto getReservation(long id) {
        return null;
    }

    public ReservationDto addReservation(CreateUpdateFlightDto createUpdateFlightDto) {
        return null;
    }

    public ReservationDto editReservation(long id, CreateUpdateFlightDto createUpdateFlightDto) {
        return null;
    }

    public void deleteReservation(long id) {
        if(!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation with id " + id + " not found.");
        }
        reservationRepository.deleteById(id);
    }
}
