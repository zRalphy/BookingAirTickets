package com.pgs.booking.model.dto;

import lombok.Value;
import lombok.Builder;
@Value
@Builder
public class CreateUpdatePassengerDto {
    String firstName;
    String lastName;
    String email;
    String country;
    String telephone;
}
