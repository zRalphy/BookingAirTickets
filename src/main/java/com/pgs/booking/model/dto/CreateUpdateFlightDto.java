package com.pgs.booking.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Value
@Builder
public class CreateUpdateFlightDto {

    @NotBlank(message = "Please enter your numberOfPassenger.")
    String numberOfPassenger;
    @NotBlank(message = "Please enter your type.")
    String type;
    @NotBlank(message = "Please enter your typeOfSeat.")
    String typeOfSeat;
    @NotBlank(message = "Please enter your typeOfLuggage.")
    String typeOfLuggage;
    @NotBlank(message = "Please enter your departureDate.")
    String departureDate;
    @NotBlank(message = "Please enter your dateOfArrival.")
    Date dateOfArrival;
}
