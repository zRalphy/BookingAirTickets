package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PassengerDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    String country;
    String telephone;
}
