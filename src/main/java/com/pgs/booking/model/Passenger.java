package com.pgs.booking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "passenger")
public class Passenger {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private final Long id = null;
    @Column(name = "firstName")
    @NotEmpty
    @NotBlank(message = "Please enter your firstName.")
    @Size(min = 2, message = "Passenger firstName should have at least 2 characters.")
    private String firstName;
    @Column(name = "lastName")
    @NotEmpty
    @NotBlank(message = "Please enter your lastName.")
    @Size(min = 2, message = "Passenger lastName should have at least 2 characters.")
    private String lastName;
    @Column(name = "email")
    @NotEmpty
    @NotBlank(message = "Please enter your email.")
    @Email
    private String email;
    @Column(name = "country")
    @NotEmpty
    @NotBlank(message = "Please enter your country.")
    private String country;
    @Column(name = "telephone")
    @NotEmpty
    @NotBlank(message = "Please enter your phoneNumber.")
    private String telephone;
}

