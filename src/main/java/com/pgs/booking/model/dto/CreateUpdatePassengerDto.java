package com.pgs.booking.model.dto;

import lombok.Value;
import lombok.Builder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Builder
public class CreateUpdatePassengerDto {
    @NotEmpty
    @NotBlank(message = "Please enter your firstName.")
    @Size(min = 2, message = "Passenger firstName should have at least 2 characters.")
    String firstName;
    @NotEmpty
    @NotBlank(message = "Please enter your lastName.")
    @Size(min = 2, message = "Passenger lastName should have at least 2 characters.")
    String lastName;
    @NotEmpty
    @NotBlank(message = "Please enter your email.")
    @Email
    String email;
    @NotEmpty
    @NotBlank(message = "Please enter your country.")
    String country;
    @NotEmpty
    @NotBlank(message = "Please enter your phoneNumber.")
    String telephone;
}
