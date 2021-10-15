package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdateFlightDtoMapper;
import com.pgs.booking.mappers.FlightDtoMapper;
import com.pgs.booking.model.Flight;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightDtoMapper flightDtoMapper;
    private final CreateUpdateFlightDtoMapper createUpdateFlightDtoMapper;


    public List<FlightDto> getFlights() {
        List<Flight> findAll = flightRepository.findAll();
        return flightDtoMapper.mapToFlightsDto(findAll);
    }

    public FlightDto getSingleFlight(long id) {
        Flight flightFromRepo = flightRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found."));
        return flightDtoMapper.mapToFlightDto(flightFromRepo);
    }

    public FlightDto addFlight(CreateUpdateFlightDto createUpdateFlightDto) {
        return null;
    }

    public FlightDto editFlight(long id,CreateUpdateFlightDto createUpdateFlightDto) {
        return null;
    }

    public void deleteFlight(long id) {
        boolean exists = flightRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException(
                    " Flight with id " + id+ " does not exists ");
        }
        flightRepository.deleteById(id);
    }
}
