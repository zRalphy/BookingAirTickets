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
    @NotBlank(message = "Please enter your firstName.")
    @Size(min = 3, max = 45, message = "Passenger firstName should have at least 3 characters.")
    String firstName;
    @NotBlank(message = "Please enter your lastName.")
    @Size(min = 3, max = 45, message = "Passenger lastName should have at least 3 characters.")
    String lastName;
    @NotBlank(message = "Please enter your email.")
    @Email
    String email;
    @NotBlank(message = "Please enter your country.")
    @Size(min = 3, max = 45, message = "Passenger country should have at least 3 characters.")
    String country;
    @NotBlank(message = "Please enter your phoneNumber.")
    @Size(min = 9, max = 45, message = "Passenger telephone should have at least 9 characters.")
    String telephone;
}
