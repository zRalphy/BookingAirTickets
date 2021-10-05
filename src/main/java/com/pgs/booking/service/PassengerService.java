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
        return passengerRepository.findAllPassenger();
    }

    public Passenger getSinglePassenger(Long id) {
        return passengerRepository.findPassengerById(id).orElseThrow();
    }

    public void addNewPassenger(Passenger passenger) {
        Optional<Passenger> geolocationOptional = passengerRepository.findPassengerById(passenger.getId());
        if(geolocationOptional.isPresent()) {
            throw new IllegalStateException("Passenger with id " + passenger.getId() + " exist in database");
        }
        passengerRepository.save(passenger);
    }

    public void updatePassenger(Long id, String firstName, String lastName, String email, String country, String telephone) {
        Passenger passenger= passengerRepository.findPassengerById(id)
                .orElseThrow(() -> new IllegalStateException(" Passenger with id " + id + " does not exists "));
        if(firstName != null &&
                firstName.length() > 0
                && !Objects.equals(passenger.getFirstName(), firstName)) {
            passenger.setFirstName(firstName);
        }

        if(lastName != null &&
                lastName.length() > 0
                && !Objects.equals(passenger.getLastName(), lastName)) {
            passenger.setLastName(lastName);
        }

        if(email != null &&
                email.length() > 0
                && !Objects.equals(passenger.getEmail(), email)) {
            Optional <Passenger> passengerEmailOptional = passengerRepository.findPassengerByEmail(email);
            if(passengerEmailOptional.isPresent()) {
                throw new IllegalStateException(" Email exist in database ");
            }
            passenger.setEmail(email);
        }

        if(country != null &&
                country.length() > 0
                && !Objects.equals(passenger.getCountry(), country)) {
            passenger.setCountry(country);
        }

        if(telephone != null &&
                telephone.length() > 0
                && !Objects.equals(passenger.getTelephone(), telephone)) {
            passenger.setTelephone(telephone);
        }
    }
}
