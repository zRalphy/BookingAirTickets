package com.pgs.booking.service;

import com.pgs.booking.model.Passenger;
import com.pgs.booking.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;

    public List<Passenger> getPassengers() {
        return passengerRepository.findAll();
    }

    public Passenger getSinglePassenger(long id) {
        return passengerRepository.findById(id).orElseThrow();
    }

    public void addNewPassenger(Passenger passenger) {
        Optional<Passenger> geolocationOptional = passengerRepository.findById(passenger.getId());
        if(geolocationOptional.isPresent()) {
            throw new IllegalStateException("Passenger with id " + passenger.getId() + " exist in database");
        }
        passengerRepository.save(passenger);
    }

    public Passenger editPassenger(Passenger passenger) {
        Passenger passengerEdited = passengerRepository.findById(passenger.getId())
                .orElseThrow(() -> new IllegalStateException(" Passenger with id " + passenger.getId() + " does not exists "));


        if(passenger.getFirstName() != null &&
                passenger.getFirstName().length() > 0
                && !Objects.equals(passengerEdited.getFirstName(), passenger.getFirstName())) {
            passengerEdited.setFirstName(passenger.getFirstName());
        }

        if(passenger.getLastName() != null &&
                passenger.getLastName().length() > 0
                && !Objects.equals(passengerEdited.getLastName(), passenger.getLastName())) {
            passengerEdited.setLastName(passenger.getLastName());
        }

        if(passenger.getEmail() != null &&
                passenger.getEmail().length() > 0
                && !Objects.equals(passengerEdited.getEmail(), passenger.getEmail())) {
            Optional <Passenger> passengerEmailOptional = passengerRepository.findPassengerByEmail(passenger.getEmail());
            if(passengerEmailOptional.isPresent()) {
                throw new IllegalStateException(" Email exist in database ");
            }
            passengerEdited.setEmail(passenger.getEmail());
        }

        if(passenger.getCountry() != null &&
                passenger.getCountry().length() > 0
                && !Objects.equals(passengerEdited.getCountry(), passenger.getCountry())) {
            passengerEdited.setCountry(passenger.getCountry());
        }

        if(passenger.getTelephone() != null &&
                passenger.getTelephone().length() > 0
                && !Objects.equals(passengerEdited.getTelephone(), passenger.getTelephone())) {
            passengerEdited.setTelephone(passenger.getTelephone());
        }
        return passengerEdited;
    }
}
