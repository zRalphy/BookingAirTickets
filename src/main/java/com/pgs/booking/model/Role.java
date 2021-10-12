package com.pgs.booking.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
