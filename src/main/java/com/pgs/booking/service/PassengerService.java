package com.pgs.booking.service;

import com.pgs.booking.controller.PassengerDtoMapper;
import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.model.Passenger;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerDtoMapper passengerDtoMapper;

    public List<PassengerDto> getPassengers() {
        List<Passenger> findAll = passengerRepository.findAll();
        return passengerDtoMapper.mapToPassengersDto(findAll);
    }

    public PassengerDto getSinglePassenger(long id) {
       Passenger passengerFromRepo = passengerRepository
               .findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + id + " not found."));
       return passengerDtoMapper.mapToPassengerDto(passengerFromRepo);
    }
/*
    public void addNewPassenger(PassengerDto passengerDto) {
        Optional<PassengerDto> PassengerOptional = passengerRepository.findByEmail(passengerDto.getEmail());
        if(PassengerOptional.isPresent()) {
            throw new IllegalStateException("Passenger with this email exist in database.");
        }
        passengerRepository.save(passengerDto);
    }
*/
/*
    public Passenger editPassenger(PassengerDto passengerDto) {

        PassengerDto passengerEditedDto = passengerRepository.findById(passengerDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger with id " + passengerDto.getId() + " not found."));


        if(passengerDto.getFirstName() != null &&
                passengerDto.getFirstName().length() > 0
                && !Objects.equals(passengerEditedDto.getFirstName(), passengerDto.getFirstName())) {
            passengerEditedDto.setFirstName(passengerDto.getFirstName());
        }

        if(passengerDto.getLastName() != null &&
                passengerDto.getLastName().length() > 0
                && !Objects.equals(passengerEditedDto.getLastName(), passengerDto.getLastName())) {
            passengerEditedDto.setLastName(passengerDto.getLastName());
        }

        if(passengerDto.getEmail() != null &&
                passengerDto.getEmail().length() > 0
                && !Objects.equals(passengerEditedDto.getEmail(), passengerDto.getEmail())) {
            Optional <PassengerDto> passengerEmailOptionalDto = passengerRepository.findByEmail(passengerDto.getEmail());
            if(passengerEmailOptionalDto.isPresent()) {
                throw new IllegalStateException("Email exist in database.");
            }
            passengerEditedDto.setEmail(passengerDto.getEmail());
        }

        if(passengerDto.getCountry() != null &&
                passengerDto.getCountry().length() > 0
                && !Objects.equals(passengerEditedDto.getCountry(), passengerDto.getCountry())) {
            passengerEditedDto.setCountry(passengerDto.getCountry());
        }

        if(passengerDto.getTelephone() != null &&
                passengerDto.getTelephone().length() > 0
                && !Objects.equals(passengerEditedDto.getTelephone(), passengerDto.getTelephone())) {
            passengerEditedDto.setTelephone(passengerDto.getTelephone());
        }
        return passengerEditedDto;
    }
 */
}
