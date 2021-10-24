package com.pgs.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.Reservation;
import com.pgs.booking.model.entity.Role;
import com.pgs.booking.model.entity.User;
import com.pgs.booking.service.ReservationService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    ReservationControllerTest() {
    }

    @BeforeEach
    public void setUP() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    private static final Role ROLE_1 = Role.builder()
            .name("USER")
            .build();

    private static final List<Role> ROLE_LIST = List.of(ROLE_1);

    private static final User USER_1 = User.builder()
            .id(2L)
            .username("user1")
            .roles(ROLE_LIST)
            .build();

    private static PassengerDto PASSENGER_DTO = PassengerDto.builder()
            .id(2L)
            .firstName("Johny")
            .lastName("Deep")
            .email("DeepJohn@gmail.com")
            .country("USA")
            .telephone("234666788")
            .build();

    private static CreateUpdatePassengerDto CREATE_UPDATE_PASSENGER_DTO = CreateUpdatePassengerDto.builder()
            .firstName("Dwayne")
            .lastName("Johnson")
            .email("JohnsonDwayne@gmail.com")
            .country("USA")
            .telephone("123456789")
            .build();

    private static ReservationDto RESERVATION_DTO = ReservationDto.builder()
            .id(1L)
            .flightId(1L)
            .userId(1L)
            .passengers(List.of(PASSENGER_DTO))
            .status(Reservation.ReservationStatus.IN_PROGRESS)
            .build();

    private static CreateUpdateReservationDto CREATE_UPDATE_RESERVATION_DTO = CreateUpdateReservationDto.builder()
            .flightId(1L)
            .passengers(List.of(CREATE_UPDATE_PASSENGER_DTO))
            .build();

    @SneakyThrows
    @Test
    void testGetReservationByFlight() {
        long id = 1L;
        given(reservationService.getReservationByFlight(id)).willReturn(Collections.singletonList(RESERVATION_DTO));
        mockMvc.perform(get("/api/reservations/flights/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(RESERVATION_DTO.getId().intValue())))
                .andExpect(jsonPath("$[0].flightId", equalTo(RESERVATION_DTO.getFlightId().intValue())))
                .andExpect(jsonPath("$[0].passengers.*", hasSize(RESERVATION_DTO.getPassengers().size())))
                .andExpect(jsonPath("$[0].passengers.[0].id").value(RESERVATION_DTO.getPassengers().get(0).getId()))
                .andExpect(jsonPath("$[0].passengers.[0].firstName").value(RESERVATION_DTO.getPassengers().get(0).getFirstName()))
                .andExpect(jsonPath("$[0].passengers.[0].lastName").value(RESERVATION_DTO.getPassengers().get(0).getLastName()))
                .andExpect(jsonPath("$[0].passengers.[0].email").value(RESERVATION_DTO.getPassengers().get(0).getEmail()))
                .andExpect(jsonPath("$[0].passengers.[0].country").value(RESERVATION_DTO.getPassengers().get(0).getCountry()))
                .andExpect(jsonPath("$[0].passengers.[0].telephone").value(RESERVATION_DTO.getPassengers().get(0).getTelephone()))
                .andExpect(jsonPath("$[0].status").value(RESERVATION_DTO.getStatus().toString()));
        verify(reservationService).getReservationByFlight(id);
    }

    @SneakyThrows
    @Test
    void testGetReservationByUser() {
        long id = 1L;
        given(reservationService.getReservationByUser(id)).willReturn(Collections.singletonList(RESERVATION_DTO));
        mockMvc.perform(get("/api/reservations/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(RESERVATION_DTO.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", equalTo(RESERVATION_DTO.getUserId().intValue())))
                .andExpect(jsonPath("$[0].passengers.*", hasSize(RESERVATION_DTO.getPassengers().size())))
                .andExpect(jsonPath("$[0].passengers.[0].id").value(RESERVATION_DTO.getPassengers().get(0).getId()))
                .andExpect(jsonPath("$[0].passengers.[0].firstName").value(RESERVATION_DTO.getPassengers().get(0).getFirstName()))
                .andExpect(jsonPath("$[0].passengers.[0].lastName").value(RESERVATION_DTO.getPassengers().get(0).getLastName()))
                .andExpect(jsonPath("$[0].passengers.[0].email").value(RESERVATION_DTO.getPassengers().get(0).getEmail()))
                .andExpect(jsonPath("$[0].passengers.[0].country").value(RESERVATION_DTO.getPassengers().get(0).getCountry()))
                .andExpect(jsonPath("$[0].passengers.[0].telephone").value(RESERVATION_DTO.getPassengers().get(0).getTelephone()))
                .andExpect(jsonPath("$[0].status").value(RESERVATION_DTO.getStatus().toString()));
        verify(reservationService).getReservationByUser(id);
    }

    @SneakyThrows
    @Test
    void testAddReservation() {
        when(reservationService.addReservation(CREATE_UPDATE_RESERVATION_DTO, USER_1)).thenReturn(RESERVATION_DTO);
        mockMvc.perform(post("/api/reservations")
                        .content(objectMapper.writeValueAsString(CREATE_UPDATE_RESERVATION_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo(RESERVATION_DTO.getId().intValue())))
                .andExpect(jsonPath("$[0].userId", equalTo(USER_1.getId().intValue())))
                .andExpect(jsonPath("$[0].passengers.*", hasSize(RESERVATION_DTO.getPassengers().size())))
                .andExpect(jsonPath("$[0].passengers.[0].id").value(RESERVATION_DTO.getPassengers().get(0).getId()))
                .andExpect(jsonPath("$[0].passengers.[0].firstName").value(RESERVATION_DTO.getPassengers().get(0).getFirstName()))
                .andExpect(jsonPath("$[0].passengers.[0].lastName").value(RESERVATION_DTO.getPassengers().get(0).getLastName()))
                .andExpect(jsonPath("$[0].passengers.[0].email").value(RESERVATION_DTO.getPassengers().get(0).getEmail()))
                .andExpect(jsonPath("$[0].passengers.[0].country").value(RESERVATION_DTO.getPassengers().get(0).getCountry()))
                .andExpect(jsonPath("$[0].passengers.[0].telephone").value(RESERVATION_DTO.getPassengers().get(0).getTelephone()))
                .andExpect(jsonPath("$[0].status").value(RESERVATION_DTO.getStatus().toString()));
        verify(reservationService).addReservation(CREATE_UPDATE_RESERVATION_DTO, USER_1);
    }

    @SneakyThrows
    @Test
    void testDeleteReservation() {
        long id = 1L;
        doNothing().when(reservationService).deleteReservation(id);
        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isOk());
        verify(reservationService).deleteReservation(id);
    }

    @SneakyThrows
    @Test
    void testRealizedReservation() {
        long id = 1L;
        when(reservationService.realizedReservation(id)).thenReturn(RESERVATION_DTO);
        mockMvc.perform(put("/api/reservations/1/realized"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(RESERVATION_DTO.getStatus().toString()));
        verify(reservationService).realizedReservation(id);
    }

    @SneakyThrows
    @Test
    void testCanceledReservation() {
        long id = 1L;
        when(reservationService.canceledReservation(id)).thenReturn(RESERVATION_DTO);
        mockMvc.perform(put("/api/reservations/1/canceled"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(RESERVATION_DTO.getStatus().toString()));
        verify(reservationService).canceledReservation(id);
    }
}