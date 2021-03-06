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
    @Size(min = 1, max = 3, message = "Airport code should have at least 1 and at most 3 characters.")
    private String code;
    @NotBlank(message = "Please enter your airport name.")
    @Size(min = 5, max = 75, message = "Airport name should have at least 5 and at most 75 characters.")
    private String name;
    @NotBlank(message = "Please enter your airport country.")
    @Size(min = 3, max = 50, message = "Country should have at least 3 and at most 50 characters.")
    private String country;
}
