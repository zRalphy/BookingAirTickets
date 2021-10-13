package com.pgs.booking.controller;

import com.pgs.booking.model.dto.CreateUpdatePassengerDto;
import com.pgs.booking.model.dto.PassengerDto;
import com.pgs.booking.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/api/passengers")
    public List<PassengerDto> getPassengers() {
        log.trace("Controller method: getPassengers.");
        return passengerService.getPassengers();
    }

    @GetMapping("/api/passengers/{id}")
    public PassengerDto getSinglePassenger(@Valid @PathVariable long id)  {
        log.trace("Controller method: getSinglePassenger.");
            return passengerService.getSinglePassenger(id);
    }

    @PostMapping("/api/passengers")
    public CreateUpdatePassengerDto addPassenger(@Valid @RequestBody CreateUpdatePassengerDto createUpdatePassengerDto) {
        log.trace("Controller method: addPassenger.");
        return passengerService.addPassenger(createUpdatePassengerDto);
    }

    @PutMapping("/api/passengers/{id}")
    public CreateUpdatePassengerDto editPassenger(@Valid @RequestBody CreateUpdatePassengerDto createUpdatePassengerDto, @PathVariable long id) {
        log.trace("Controller method: editPassenger.");
        return passengerService.editPassenger(id, createUpdatePassengerDto);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
