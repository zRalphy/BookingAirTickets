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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerDtoMapper passengerDtoMapper;
    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerService.class);


    public List<PassengerDto> getPassengers() {
        LOGGER.trace("Entering method getPassengers.");
        List<Passenger> findAll = passengerRepository.findAll();
        return passengerDtoMapper.mapToPassengersDto(findAll);
    }

    public PassengerDto getSinglePassenger(long id) {
        LOGGER.trace("Entering method getSinglePassenger.");
        Passenger passengerFromRepo = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + id + " not found."));
        return passengerDtoMapper.mapToPassengerDto(passengerFromRepo);
    }

    public Passenger addNewPassenger(CreateUpdatePassengerDto createUpdatePassengerDto) {
        LOGGER.trace("Entering method addNewPassenger.");
        Optional<Passenger> passengerOptionalEmail = passengerRepository
                .findByEmail(createUpdatePassengerDtoMapper
                        .mapToPassenger(createUpdatePassengerDto)
                        .getEmail());
        if(passengerOptionalEmail.isPresent()) {
            LOGGER.warn("Passenger with this email exist in database.");
            //throw new IllegalStateException("Passenger with this email exist in database.");
        }
        LOGGER.info("Passenger saved in repository.");
        return passengerRepository
                .save(createUpdatePassengerDtoMapper.mapToPassenger(createUpdatePassengerDto));

    }

    public Passenger editPassenger (long id, CreateUpdatePassengerDto createUpdatePassengerDto) {
        LOGGER.trace("Entering method editPassenger.");
        Passenger passengerToEdit = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + id + " not found."));

        if(passengerToEdit.getFirstName() != null &&
                passengerToEdit.getFirstName().length() > 0
                && !Objects.equals(passengerToEdit.getFirstName(), createUpdatePassengerDto.getFirstName())) {
            passengerToEdit.setFirstName(createUpdatePassengerDto.getFirstName());
            LOGGER.info("Passenger firstNameField was change.");
        }

        if(passengerToEdit.getLastName() != null &&
                passengerToEdit.getLastName().length() > 0
                && !Objects.equals(passengerToEdit.getLastName(), createUpdatePassengerDto.getLastName())) {
            passengerToEdit.setLastName(createUpdatePassengerDto.getLastName());
            LOGGER.info("Passenger lastNameField was change.");
        }

        if(passengerToEdit.getEmail() != null &&
                passengerToEdit.getEmail().length() > 0
                && !Objects.equals(passengerToEdit.getEmail(), createUpdatePassengerDto.getEmail())) {
            Optional<Passenger> passengerOptionalEmail = passengerRepository
                    .findByEmail(createUpdatePassengerDto
                            .getEmail());
            if(passengerOptionalEmail.isPresent()) {
                LOGGER.warn("Passenger with this email exist in database.");
                //throw new IllegalStateException("Email exist in database.");
            }
            passengerToEdit.setEmail(createUpdatePassengerDto.getEmail());
            LOGGER.info("Passenger emailField was change.");
        }

        if(passengerToEdit.getCountry() != null &&
                passengerToEdit.getCountry().length() > 0
                && !Objects.equals(passengerToEdit.getCountry(), createUpdatePassengerDto.getCountry())) {
            passengerToEdit.setCountry(createUpdatePassengerDto.getCountry());
            LOGGER.info("Passenger countryField was change.");
        }

        if(passengerToEdit.getTelephone() != null &&
                passengerToEdit.getTelephone().length() > 0
                && !Objects.equals(passengerToEdit.getTelephone(), createUpdatePassengerDto.getTelephone())) {
            passengerToEdit.setTelephone(createUpdatePassengerDto.getTelephone());
            LOGGER.info("Passenger telephoneField was change.");
        }
        LOGGER.info("Passenger saved in repository.");
        return passengerRepository
                .save(passengerToEdit);
    }
}
