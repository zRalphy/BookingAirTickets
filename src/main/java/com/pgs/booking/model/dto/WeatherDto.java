package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WeatherDto {
    String temperature;
    String humidity;
    String pressure;
    String windSpeed;
}
