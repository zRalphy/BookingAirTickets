package com.pgs.booking.mappers;

import com.pgs.booking.model.Reservation;
import com.pgs.booking.model.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationDtoMapper {

    private final PassengerDtoMapper passengerDtoMapper;

    public List<ReservationDto> mapToPassengersDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }

    public ReservationDto mapToReservationDto(Reservation reservation) {
        var passengersList = reservation.getPassengers();
        var passengersDTOList = passengerDtoMapper.mapToPassengersDto(passengersList);

        return ReservationDto.builder()
                .id(reservation.getId())
                .status(reservation.getStatus())
                .flight(reservation.getFlight())
                .passengers(passengersDTOList)
                .build();
    }
}
