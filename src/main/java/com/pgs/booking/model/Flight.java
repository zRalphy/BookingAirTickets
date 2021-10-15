package com.pgs.booking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private final Long id = null;
    @Column(name = "type")
    private TypeOfFlight type;
    @Column(name = "departureAirport")
    private String departureAirport;
    @Column(name = "arrivalAirport")
    private String arrivalAirport;
    @Column(name = "departureDate")
    private LocalDateTime departureDate;
    @Column(name = "dateOfArrival")
    private LocalDateTime arrivalDate;

    public enum TypeOfFlight {
        ECONOMY, PREMIUM, BUSINESS
    }
}
