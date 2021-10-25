package com.pgs.booking.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateAirportDto {
    @NotBlank(message = "Please enter your airport code.")
    @Size(min = 3, max = 6, message = "Airport code should have at least 3 and at most 6 characters.")
    private String code;
    @NotBlank(message = "Please enter your airport name.")
    @Size(min = 5, max = 45, message = "Airport name should have at least 5 and at most 45 characters.")
    private String name;
    @NotBlank(message = "Please enter your airport country.")
    @Size(min = 3, max = 45, message = "Country should have at least 3 and at most 45 characters.")
    private String country;
    @NotBlank(message = "Please enter your airport city.")
    @Size(min = 3, max = 45, message = "City should have at least 3 and at most 45 characters.")
    private String city;
}
