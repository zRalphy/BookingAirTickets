package com.pgs.booking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdatePassengerDto {
    @NotBlank(message = "Please enter your firstName.")
    @Size(min = 3, message = "Passenger firstName should have at least 3 characters.")
    @Size(max = 45, message = "Passenger firstName should have at most 45 characters.")
    private String firstName;
    @NotBlank(message = "Please enter your lastName.")
    @Size(min = 3, message = "Passenger lastName should have at least 3 characters.")
    @Size(max = 45, message = "Passenger lastName should have at most 45 characters.")
    private String lastName;
    @NotBlank(message = "Please enter your email.")
    @Email
    private String email;
    @NotBlank(message = "Please enter your country.")
    @Size(min = 3, message = "Passenger country should have at least 3 characters.")
    @Size(max = 45, message = "Passenger country should have at most 45 characters.")
    private String country;
    @NotBlank(message = "Please enter your phoneNumber.")
    @Size(min = 9, message = "Passenger telephone should have at least 9 characters.")
    @Size(max = 45, message = "Passenger telephone should have at most 45 characters.")
    private String telephone;
}
