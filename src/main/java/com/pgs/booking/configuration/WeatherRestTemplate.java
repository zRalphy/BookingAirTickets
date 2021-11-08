package com.pgs.booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WeatherRestTemplate {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
