package com.pgs.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        if(ex instanceof ResourceNotFoundException){
            ErrorResponse error = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
            logger.error("Resource was not found.", ex);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else {
            HttpStatus internalConflict = HttpStatus.INTERNAL_SERVER_ERROR;
            ErrorResponse error = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
            logger.error("Oops, internal server error.", ex);
            return new ResponseEntity<>(error, internalConflict);
        }
    }
}
