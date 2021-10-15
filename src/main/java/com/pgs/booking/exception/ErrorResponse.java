package com.pgs.booking.exception;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ErrorResponse {
    LocalDateTime timestamp;
    String message;
    String details;
}



