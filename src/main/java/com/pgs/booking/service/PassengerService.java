package com.pgs.booking.service;

import com.pgs.booking.controller.CreateUpdatePassengerDtoMapper;
import com.pgs.booking.controller.PassengerController;
import com.pgs.booking.controller.PassengerDtoMapper;
import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Validated
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

    public Passenger addNewPassenger(CreateUpdatePassengerDto createUpdatePassengerDto) {
        log.trace("Entering service method addNewPassenger.");
        Optional<Passenger> passengerOptionalEmail = passengerRepository
                .findByEmail(createUpdatePassengerDtoMapper
                        .mapToPassenger(createUpdatePassengerDto)
                        .getEmail());
        if(passengerOptionalEmail.isPresent()) {
            log.warn("Passenger with this email exist in database.");
            throw new IllegalStateException("Passenger with this email exist in database.");
        }
        return passengerRepository
                .save(createUpdatePassengerDtoMapper.mapToPassenger(createUpdatePassengerDto));

    }

    public Passenger editPassenger (long id, CreateUpdatePassengerDto createUpdatePassengerDto) {
        log.trace("Entering service method editPassenger.");
        Passenger passengerToEdit = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + id + " not found."));

        if(passengerToEdit.getFirstName() != null &&
                passengerToEdit.getFirstName().length() > 0
                && !Objects.equals(passengerToEdit.getFirstName(), createUpdatePassengerDto.getFirstName())) {
            passengerToEdit.setFirstName(createUpdatePassengerDto.getFirstName());
        }

        if(passengerToEdit.getLastName() != null &&
                passengerToEdit.getLastName().length() > 0
                && !Objects.equals(passengerToEdit.getLastName(), createUpdatePassengerDto.getLastName())) {
            passengerToEdit.setLastName(createUpdatePassengerDto.getLastName());
        }

        if(passengerToEdit.getEmail() != null &&
                passengerToEdit.getEmail().length() > 0
                && !Objects.equals(passengerToEdit.getEmail(), createUpdatePassengerDto.getEmail())) {
            Optional<Passenger> passengerOptionalEmail = passengerRepository
                    .findByEmail(createUpdatePassengerDto
                            .getEmail());
            if(passengerOptionalEmail.isPresent()) {
                log.warn("Passenger with this email exist in database.");
                throw new IllegalStateException("Email exist in database.");
            }
            passengerToEdit.setEmail(createUpdatePassengerDto.getEmail());
        }

        if(passengerToEdit.getCountry() != null &&
                passengerToEdit.getCountry().length() > 0
                && !Objects.equals(passengerToEdit.getCountry(), createUpdatePassengerDto.getCountry())) {
            passengerToEdit.setCountry(createUpdatePassengerDto.getCountry());
        }

        if(passengerToEdit.getTelephone() != null &&
                passengerToEdit.getTelephone().length() > 0
                && !Objects.equals(passengerToEdit.getTelephone(), createUpdatePassengerDto.getTelephone())) {
            passengerToEdit.setTelephone(createUpdatePassengerDto.getTelephone());
        }
        return passengerRepository
                .save(passengerToEdit);
    }
}
