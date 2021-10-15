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
    @Enumerated(EnumType.STRING)
    private TypeOfFlight type;
    @Column(name = "departureDate")
    private LocalDateTime departureDate;
    @Column(name = "arrivalDate")
    private LocalDateTime arrivalDate;

    public enum TypeOfFlight {
        ECONOMY, PREMIUM, BUSINESS
    }
}
