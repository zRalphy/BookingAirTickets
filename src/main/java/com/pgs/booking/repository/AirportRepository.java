package com.pgs.booking.repository;

import com.pgs.booking.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    @Query("SELECT a FROM Airport a WHERE a.code = :code")
    Airport findAirportByCode(@Param("code") String code);
}
