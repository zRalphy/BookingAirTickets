package com.pgs.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.booking.exception.ResourceNotFoundException;
import com.pgs.booking.model.dto.WeatherDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherService {
    @Value("${openwaether.api-key}")
    private String keyValue;
    private static final String mode = "json";
    private static final String units = "imperial";
    private final ObjectMapper objectMapper;
    @Autowired
    private final RestTemplate restTemplate;

    public WeatherDto getWeatherByCity(String city) {
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + keyValue + "&mode=" + mode + "&units=" + units;
        ResponseEntity<String> response = restTemplate.getForEntity(weatherUrl, String.class);
        JsonNode root;
        try {
            root = objectMapper.readTree(response.getBody());
            JsonNode main = root.get("main");
            JsonNode wind = root.get("wind");
            var temperature = main.get("temp");
            var humidity = main.get("humidity");
            var pressure = main.get("pressure");
            var windSpeed = wind.get("speed");
            return WeatherDto.builder()
                    .temperature(fromFahrenheitToCelsius(temperature.asInt()) + "°C")
                    .humidity(humidity.asText() + "%")
                    .pressure(pressure.asText() + "hPa")
                    .windSpeed(windSpeed.asText() + "mph")
                    .build();
        } catch (JsonProcessingException e) {
            log.error("Can not parse JSON content", e);
            throw new ResourceNotFoundException("Can not load weather from server");
        }
    }

    private String fromFahrenheitToCelsius(Integer temp) {
        int tempInCelsius = 5 * (temp - 32) / 9;
        return Integer.toString(tempInCelsius);
    }
}
