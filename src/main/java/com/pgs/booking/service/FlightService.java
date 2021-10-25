package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdateFlightDtoMapper;
import com.pgs.booking.mappers.FlightDtoMapper;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.model.entity.Flight;
import com.pgs.booking.model.entity.Reservation;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightDtoMapper flightDtoMapper;
    private final CreateUpdateFlightDtoMapper createUpdateFlightDtoMapper;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public List<FlightDto> getFlights() {
        List<Flight> allFlights = flightRepository.findAll();
        return flightDtoMapper.mapToFlightsDto(allFlights);
    }

    public FlightDto getFlight(long id) {
        Flight flightFromRepo = flightRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found."));
        return flightDtoMapper.mapToFlightDto(flightFromRepo);
    }

    public FlightDto addFlight(CreateUpdateFlightDto createUpdateFlightDto) {
        Flight flight = flightRepository.save(createUpdateFlightDtoMapper.mapToFlight(createUpdateFlightDto));
        return flightDtoMapper.mapToFlightDto(flight);
    }

    public FlightDto editFlight(long id, CreateUpdateFlightDto createUpdateFlightDto) {
        Flight flightToEdit = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found."));

        flightToEdit.setType(createUpdateFlightDto.getType());
        flightToEdit.setDepartureDate(createUpdateFlightDto.getDepartureDate());
        flightToEdit.setArrivalDate(createUpdateFlightDto.getArrivalDate());
        Flight flightToSave = flightRepository.save(flightToEdit);
        return flightDtoMapper.mapToFlightDto(flightToSave);
    }

    public void deleteFlight(long id) {
        if(!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight with id " + id + " not found.");
        }
        flightRepository.deleteById(id);
        List<Reservation> reservationsCanceled = reservationRepository.findAllByFlightId(id);
        reservationsCanceled.stream().mapToLong(Reservation::getId).forEach(reservationService::canceledReservation);
    }
}
