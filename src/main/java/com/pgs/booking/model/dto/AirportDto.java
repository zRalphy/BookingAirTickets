package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AirportDto {
    Long id;
    String code;
    String name;
    String country;
    String city;
}
