package com.pgs.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.booking.BookingAirTicketsApplication;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.service.PassengerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookingAirTicketsApplication.class)
class PassengerControllerTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PassengerService passengerService;
    private MockMvc mockMvc;

    private static PassengerDto PASSENGER_DTO = PassengerDto.builder()
            .id(5L)
            .firstName("John")
            .lastName("Thin")
            .email("ThinJohn@gmail.com")
            .country("USA")
            .telephone("123456789")
            .build();

    @BeforeEach
    public void setUP() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac).build();
    }

    @SneakyThrows
    @Test
    void testGetPassengers() {
        var passengerDtoList = List.of(PASSENGER_DTO);
        given(passengerService.getPassengers()).willReturn(passengerDtoList);
        mockMvc.perform(get("/api/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(PASSENGER_DTO.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", equalTo(PASSENGER_DTO.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", equalTo(PASSENGER_DTO.getLastName())))
                .andExpect(jsonPath("$[0].email", equalTo(PASSENGER_DTO.getEmail())))
                .andExpect(jsonPath("$[0].country", equalTo(PASSENGER_DTO.getCountry())))
                .andExpect(jsonPath("$[0].telephone", equalTo(PASSENGER_DTO.getTelephone())));
        verify(passengerService).getPassengers();
    }

    @SneakyThrows
    @Test
    void testGetSinglePassenger() {
        given(passengerService.getSinglePassenger(5L)).willReturn(PASSENGER_DTO);
        mockMvc.perform(get("/api/passengers/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", equalTo(PASSENGER_DTO.getId().intValue())))
                .andExpect(jsonPath("firstName", equalTo(PASSENGER_DTO.getFirstName())))
                .andExpect(jsonPath("lastName", equalTo(PASSENGER_DTO.getLastName())))
                .andExpect(jsonPath("email", equalTo(PASSENGER_DTO.getEmail())))
                .andExpect(jsonPath("country", equalTo(PASSENGER_DTO.getCountry())))
                .andExpect(jsonPath("telephone", equalTo(PASSENGER_DTO.getTelephone())));
        verify(passengerService).getSinglePassenger(5L);
    }
}