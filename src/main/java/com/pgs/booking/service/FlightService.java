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
        List<Flight> allFlights = flightRepository.findAll();
        return flightDtoMapper.mapToFlightsDto(allFlights);
    }

    public FlightDto getSingleFlight(long id) {
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
        boolean exists = flightRepository.existsById(id);
        if(!exists) {
            throw new ResourceNotFoundException("Flight with id " + id + " not found.");
        }
        flightRepository.deleteById(id);
    }
}
