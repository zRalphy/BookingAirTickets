package com.pgs.booking.model.dto;

import lombok.NonNull;
import lombok.Value;
import lombok.Builder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
@Builder
public class PassengerDto {
    Long id;
    @NotEmpty
    @NonNull
    @NotBlank(message = "Please enter your firstName.")
    @Size(min = 2, message = "Passenger firstName should have at least 2 characters.")
    String firstName;
    @NotEmpty
    @NonNull
    @NotBlank(message = "Please enter your lastName.")
    @Size(min = 2, message = "Passenger lastName should have at least 2 characters.")
    String lastName;
    @NotEmpty
    @NotBlank(message = "Please enter your email.")
    @Email
    String email;
    @NotEmpty
    @NonNull
    @NotBlank(message = "Please enter your country.")
    String country;
    @NotEmpty
    @NonNull
    @NotBlank(message = "Please enter your phoneNumber.")
    String telephone;
}
