package com.pgs.booking.model.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUpdateReservationDto {
    @NotNull
    private Long flightId;
    @NotEmpty
    private List<CreateUpdatePassengerDto> passengers;
}
