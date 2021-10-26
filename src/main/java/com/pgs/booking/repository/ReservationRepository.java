package com.pgs.booking.repository;

import com.pgs.booking.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByFlightId(Long id);
    List<Reservation> findAllByUserId(Long id);

    @Query("SELECT r FROM Reservation r WHERE r.id = :id")
    Reservation findReservationById(@Param("id") Long id);
}
