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
    @Column(name = "numberOfPassenger")
    private String numberOfPassenger;
    @Column(name = "type")
    private String type;
    @Column(name = "typeOfSeat")
    private String typeOfSeat;
    @Column(name = "typeOfLuggage")
    private String typeOfLuggage;
    @Column(name = "departureDate")
    private LocalDateTime departureDate;
    @Column(name = "dateOfArrival")
    private LocalDateTime dateOfArrival;
}
