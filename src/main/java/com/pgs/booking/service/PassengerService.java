package com.pgs.booking.service;

import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.mappers.CreateUpdatePassengerDtoMapper;
import com.pgs.booking.mappers.PassengerDtoMapper;
import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerDtoMapper passengerDtoMapper;
    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper;


    public List<PassengerDto> getPassengers() {
        log.trace("Entering service method getPassengers.");
        List<Passenger> findAll = passengerRepository.findAll();
        return passengerDtoMapper.mapToPassengersDto(findAll);
    }

    public PassengerDto getSinglePassenger(long id) {
        log.trace("Entering service method getSinglePassenger.");
        Passenger passengerFromRepo = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + id + " not found."));
        return passengerDtoMapper.mapToPassengerDto(passengerFromRepo);
    }

    public PassengerDto addPassenger(CreateUpdatePassengerDto createUpdatePassengerDto) {
        log.trace("Entering service method addNewPassenger.");
        Optional<Passenger> passengerOptionalEmail = passengerRepository
                .findByEmail(createUpdatePassengerDtoMapper
                        .mapToPassenger(createUpdatePassengerDto)
                        .getEmail());
        if(passengerOptionalEmail.isPresent()) {
            log.warn("Passenger with this email exist in database.");
            throw new IllegalStateException("Passenger with this email exist in database.");
        }
        Passenger passenger = passengerRepository.save(createUpdatePassengerDtoMapper.mapToPassenger(createUpdatePassengerDto));
        return passengerDtoMapper.mapToPassengerDto(passenger);
    }

    public PassengerDto editPassenger (long id, CreateUpdatePassengerDto createUpdatePassengerDto) {
        log.trace("Entering service method editPassenger.");
        Passenger passengerToEdit = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + id + " not found."));
        Passenger passengerToSave;
        passengerToSave = passengerRepository.save(passengerToEdit);
        return passengerDtoMapper.mapToPassengerDto(passengerToSave);
    }
}
