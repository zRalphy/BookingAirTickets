package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdateFlightDtoMapper;
import com.pgs.booking.mappers.FlightDtoMapper;
import com.pgs.booking.model.dto.CreateUpdateFlightDto;
import com.pgs.booking.model.dto.FlightDto;
import com.pgs.booking.model.entity.Airport;
import com.pgs.booking.model.entity.Flight;
import com.pgs.booking.model.entity.Reservation;
import com.pgs.booking.repository.AirportRepository;
import com.pgs.booking.repository.FlightRepository;
import com.pgs.booking.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightDtoMapper flightDtoMapper;
    private final CreateUpdateFlightDtoMapper createUpdateFlightDtoMapper;
    private final ReservationRepository reservationRepository;
    private final AirportRepository airportRepository;

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
        Optional<Airport> departureAirport = airportRepository.findAirportByCode_Opt(createUpdateFlightDto.getDepartureAirportIataCode());
        Optional<Airport> arrivalAirport = airportRepository.findAirportByCode_Opt(createUpdateFlightDto.getArrivalAirportIataCode());
        if (departureAirport.isEmpty() || arrivalAirport.isEmpty()) {
            throw new ResourceNotFoundException("DepartureAirportIataCode or ArrivalAirportIataCode not exist in database.");
        }
        Flight flightToEdit = createUpdateFlightDtoMapper.mapToFlight(createUpdateFlightDto);
        flightToEdit.setDepartureAirport(departureAirport.get());
        flightToEdit.setArrivalAirport(arrivalAirport.get());
        Flight flightToSave = flightRepository.save(flightToEdit);
        return flightDtoMapper.mapToFlightDto(flightToSave);
    }

    public FlightDto editFlight(long id, CreateUpdateFlightDto createUpdateFlightDto) {
        Flight flightToEdit = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight with id " + id + " not found."));

        Optional<Airport> departureAirport = airportRepository.findAirportByCode_Opt(createUpdateFlightDto.getDepartureAirportIataCode());
        Optional<Airport> arrivalAirport = airportRepository.findAirportByCode_Opt(createUpdateFlightDto.getArrivalAirportIataCode());
        if (departureAirport.isEmpty() || arrivalAirport.isEmpty()) {
            throw new ResourceNotFoundException("DepartureAirportIataCode or ArrivalAirportIataCode not exist in database.");
        }
        flightToEdit.setType(createUpdateFlightDto.getType());
        flightToEdit.setDepartureDate(createUpdateFlightDto.getDepartureDate());
        flightToEdit.setArrivalDate(createUpdateFlightDto.getArrivalDate());
        flightToEdit.setDepartureAirport(departureAirport.get());
        flightToEdit.setArrivalAirport(arrivalAirport.get());
        Flight flightToSave = flightRepository.save(flightToEdit);
        return flightDtoMapper.mapToFlightDto(flightToSave);
    }

    @Transactional
    public void deleteFlight(long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight with id " + id + " not found.");
        }
        List<Reservation> reservationsCanceled = reservationRepository.findAllByFlightId(id);
        reservationsCanceled.forEach((reservation) -> {
            reservation.setFlight(null);
            reservation.setStatus(Reservation.ReservationStatus.CANCELED);
        });
        flightRepository.deleteById(id);
    }
}
