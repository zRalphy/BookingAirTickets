package com.pgs.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.service.PassengerService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

    private static CreateUpdatePassengerDto CREATE_UPDATE_PASSENGER_DTO = CreateUpdatePassengerDto.builder()
            .firstName("Dwayne")
            .lastName("Johnson")
            .email("JohnsonDwayne@gmail.com")
            .country("USA")
            .telephone("123456789")
            .build();

    @BeforeEach
    public void setUP() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    @WithMockUser(authorities = "ADMIN")
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

    @WithMockUser(authorities = "ADMIN")
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

    @WithMockUser(authorities = "ADMIN")
    @SneakyThrows
    @Test
    void testAddPassenger() {
        when(passengerService.addPassenger(CREATE_UPDATE_PASSENGER_DTO)).thenReturn(PASSENGER_DTO);
        mockMvc.perform(post("/api/passengers")
                        .content(objectMapper.writeValueAsString(CREATE_UPDATE_PASSENGER_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.lastName").exists())
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.country").exists())
                .andExpect(jsonPath("$.telephone").exists());
        verify(passengerService).addPassenger(CREATE_UPDATE_PASSENGER_DTO);
    }

    @WithMockUser(authorities = "STAFF")
    @SneakyThrows
    @Test
    void testEditPassenger() {
        long id = 5L;
        when(passengerService.editPassenger(id, CREATE_UPDATE_PASSENGER_DTO)).thenReturn(PASSENGER_DTO);
        mockMvc.perform(put("/api/passengers/" + id)
                        .content(objectMapper.writeValueAsString(CREATE_UPDATE_PASSENGER_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(PASSENGER_DTO.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(PASSENGER_DTO.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(PASSENGER_DTO.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value(PASSENGER_DTO.getCountry()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telephone").value(PASSENGER_DTO.getTelephone()));
        verify(passengerService).editPassenger(id, CREATE_UPDATE_PASSENGER_DTO);
    }
}