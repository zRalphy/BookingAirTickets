package com.pgs.booking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String telephone;
    private String telephone2;
}

