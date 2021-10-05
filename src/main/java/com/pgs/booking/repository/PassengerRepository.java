package com.pgs.booking.repository;

import com.pgs.booking.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findAllPassenger();
    Optional<Passenger> findPassengerById(Long id);
    Optional<Passenger> findPassengerByEmail(String email);
}
