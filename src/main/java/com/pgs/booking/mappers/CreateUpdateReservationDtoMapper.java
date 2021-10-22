package com.pgs.booking.mappers;

import com.pgs.booking.model.entity.Reservation;
import com.pgs.booking.model.dto.CreateUpdateReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateUpdateReservationDtoMapper {

    private final CreateUpdatePassengerDtoMapper createUpdatePassengerDtoMapper;

    public List<CreateUpdateReservationDto> mapToCreateReservationsDto(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::mapToCreateReservationDto)
                .collect(Collectors.toList());
    }

    public List<Reservation> mapToReservations(List<CreateUpdateReservationDto> createUpdateReservationsDto) {
        return createUpdateReservationsDto.stream()
                .map(this::mapToReservation)
                .collect(Collectors.toList());
    }

    public CreateUpdateReservationDto mapToCreateReservationDto(Reservation reservation) {
        var passengersList = reservation.getPassengers();
        var flightId = reservation.getFlight().getId();
        var createUpdatePassengersDTOList = createUpdatePassengerDtoMapper.mapToCreatePassengersDto(passengersList);
        return CreateUpdateReservationDto.builder()
                .flightId(flightId)
                .passengers(createUpdatePassengersDTOList)
                .build();
    }

    public Reservation mapToReservation(CreateUpdateReservationDto createUpdateReservationDto) {
        var createUpdatePassengerDtoList = createUpdateReservationDto.getPassengers();
        var passengersList = createUpdatePassengerDtoMapper.mapToPassengers(createUpdatePassengerDtoList);
        Reservation reservation = new Reservation();
        reservation.setPassengers(passengersList);
        return reservation;
    }
}
