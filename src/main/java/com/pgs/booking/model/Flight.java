package com.pgs.booking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

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
    private Date typeOfLuggage;
    @Column(name = "departureDate")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private String departureDate;
    @Column(name = "dateOfArrival")
    @DateTimeFormat(pattern="dd/MM/yyyy")
    private Date dateOfArrival;
}
