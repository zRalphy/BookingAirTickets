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
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
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
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @SneakyThrows
    @Test
    void testGetPassengers() {
        String accessToken = obtainAccessToken();
        var passengerDtoList = List.of(PASSENGER_DTO);
        given(passengerService.getPassengers()).willReturn(passengerDtoList);
        mockMvc.perform(get("/api/passengers")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
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
        String accessToken = obtainAccessToken();
        given(passengerService.getSinglePassenger(5L)).willReturn(PASSENGER_DTO);
        mockMvc.perform(get("/api/passengers/5")
                        .header("Authorization", "Bearer " + accessToken)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id", equalTo(PASSENGER_DTO.getId().intValue())))
                .andExpect(jsonPath("firstName", equalTo(PASSENGER_DTO.getFirstName())))
                .andExpect(jsonPath("lastName", equalTo(PASSENGER_DTO.getLastName())))
                .andExpect(jsonPath("email", equalTo(PASSENGER_DTO.getEmail())))
                .andExpect(jsonPath("country", equalTo(PASSENGER_DTO.getCountry())))
                .andExpect(jsonPath("telephone", equalTo(PASSENGER_DTO.getTelephone())));
        verify(passengerService).getSinglePassenger(5L);
    }

    @SneakyThrows
    @Test
    void testGetPassengersUnauthenticated() {
        var passengerDtoList = List.of(PASSENGER_DTO);
        given(passengerService.getPassengers()).willReturn(passengerDtoList);
        mockMvc.perform(get("/api/passengers"))
                .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    @Test
    void testGetSinglePassengerUnauthenticated() {
        given(passengerService.getSinglePassenger(5L)).willReturn(PASSENGER_DTO);
        mockMvc.perform(get("/api/passengers/5"))
                .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    private String obtainAccessToken() {
        ResultActions resultActions = performActionForOauthToken();
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @SneakyThrows
    private ResultActions performActionForOauthToken() {
        return mockMvc
                .perform(post("/oauth/token")
                        .content(setParamsForRequest("user", "user"))
                        .with(httpBasic("ClientId", "secret"))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @SneakyThrows
    private String setParamsForRequest(String username, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);

        return objectMapper.writeValueAsString(params);
    }
}