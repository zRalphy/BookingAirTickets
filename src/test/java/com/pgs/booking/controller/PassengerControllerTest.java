package com.pgs.booking.controller;

import com.pgs.booking.BookingAirTicketsApplication;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

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

    @Test
    void testGetPassengers() throws Exception {
        String accessToken = obtainAccessToken("ala", "kot");
        var passengerDtoList = List.of(PASSENGER_DTO);
        given(passengerService.getPassengers()).willReturn(passengerDtoList);
        mockMvc.perform(get("/api/passengers").header("Authorization", "Bearer " + accessToken))
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

    @WithMockUser
    @Test
    void testGetSinglePassenger() throws Exception {
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

    @Test
    void testGetPassengersUnauthenticated() throws Exception {
        var passengerDtoList = List.of(PASSENGER_DTO);
        given(passengerService.getPassengers()).willReturn(passengerDtoList);
        mockMvc.perform(get("/api/passengers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetSinglePassengerUnauthenticated() throws Exception {
        given(passengerService.getSinglePassenger(5L)).willReturn(PASSENGER_DTO);
        mockMvc.perform(get("/api/passengers/5"))
                .andExpect(status().isUnauthorized());
    }

    private String obtainAccessToken(String username, String password) throws Exception {
        var params = setParamsForRequest(username, password);
        ResultActions resultActions = performActionForOauthToken(params);
        String resultString = resultActions.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    private MultiValueMap<String, String> setParamsForRequest(String username, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "Client_id");
        params.add("username", username);
        params.add("password", password);
        return params;
    }

    private ResultActions performActionForOauthToken(MultiValueMap<String, String> params) throws Exception {
        return mockMvc
                .perform(post("/oauth/token")
                        .params(params)
                        .with(httpBasic("Client_id", "secret"))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }
}