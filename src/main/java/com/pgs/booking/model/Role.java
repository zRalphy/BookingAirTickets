package com.pgs.booking.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}
