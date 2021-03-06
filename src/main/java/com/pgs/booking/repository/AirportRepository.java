package com.pgs.booking.repository;

import com.pgs.booking.model.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query("SELECT a FROM Airport a WHERE a.code = :code")
    Optional<Airport> findAirportByCode_Opt(@Param("code") String code);

    @Query("SELECT a FROM Airport a WHERE a.code = :code")
    Airport findAirportByIataCode(@Param("code") String code);
}
