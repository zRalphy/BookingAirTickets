package com.pgs.booking.mappers;

import com.pgs.booking.model.dto.ReservationDto;
import com.pgs.booking.model.entity.Flight;
import com.pgs.booking.model.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationDtoMapper {

    private final PassengerDtoMapper passengerDtoMapper;

    public List<ReservationDto> mapToReservationsDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }

    public ReservationDto mapToReservationDto(Reservation reservation) {
        var passengersList = reservation.getPassengers();
        var passengersDTOList = passengerDtoMapper.mapToPassengersDto(passengersList);
        var userId = reservation.getUser().getId();
        return ReservationDto.builder()
                .id(reservation.getId())
                .status(reservation.getStatus())
                .flightId(Optional.ofNullable(reservation.getFlight()).map(Flight::getId).orElse(null))
                .userId(userId)
                .passengers(passengersDTOList)
                .build();
    }
}
