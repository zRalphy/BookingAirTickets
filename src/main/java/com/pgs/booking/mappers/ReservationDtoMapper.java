package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationDtoMapper {

    private final PassengerDtoMapper passengerDtoMapper;
    private final FlightDtoMapper flightDtoMapper;

    public List<ReservationDto> mapToReservationsDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }

    public ReservationDto mapToReservationDto(Reservation reservation) {
        var passengersList = reservation.getPassengers();
        var passengersDTOList = passengerDtoMapper.mapToPassengersDto(passengersList);
        var flightDto = flightDtoMapper.mapToFlightDto(reservation.getFlight());
        var userId = reservation.getUser().getId();
        return ReservationDto.builder()
                .id(reservation.getId())
                .status(reservation.getStatus())
                .flightId(flightDto.getId())
                .userId(userId)
                .passengers(passengersDTOList)
                .build();
    }
}