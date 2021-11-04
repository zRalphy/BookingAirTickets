package com.pgs.booking.controller;

import com.pgs.booking.model.dto.WeatherDto;
import com.pgs.booking.service.WeatherService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'USER')")
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    @GetMapping("/")
    public WeatherDto getWeatherByCity(@RequestParam("city") String city) {
        return weatherService.getWeatherByCity(city);
    }
}
