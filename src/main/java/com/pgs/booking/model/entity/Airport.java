package com.pgs.booking.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Builder
@Table(name = "airport")
public class Airport {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "departureAirportCode")
    private String departureAirportCode;
    @Column(name = "arrivalAirportCode")
    private String arrivalAirportCode;
    @Column(name = "departureAirportName")
    private String departureAirportName;
    @Column(name = "arrivalAirportName")
    private String arrivalAirportName;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
}
