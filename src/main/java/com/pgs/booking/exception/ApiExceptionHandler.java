package com.pgs.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class, MethodArgumentNotValidException.class})
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, MethodArgumentNotValidException mex, WebRequest request) {
        if(ex instanceof ResourceNotFoundException){
            ErrorResponse error = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
            logger.error("Resource was not found.", ex);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else if(ex instanceof MethodArgumentNotValidException) {
            Map<String, String> mapOfErrors = new HashMap<>();
            for (ObjectError error : mex.getBindingResult().getAllErrors()) {
                String fieldName = ((FieldError)error).getField();
                String errorMessage = error.getDefaultMessage();
                mapOfErrors.put(fieldName, errorMessage);
            }
            ErrorResponse errorValid = new ErrorResponse(new Date(), mex.getMessage(), request.getDescription(false), mapOfErrors);
            return new ResponseEntity<>(errorValid, HttpStatus.BAD_REQUEST);
        } else {
            HttpStatus internalConflict = HttpStatus.INTERNAL_SERVER_ERROR;
            ErrorResponse error = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
            logger.error("Oops, internal server error.", ex);
            return new ResponseEntity<>(error, internalConflict);
        }
    }
}
