package com.pgs.booking.exception;

import java.util.Date;
import lombok.Value;

@Value
public class ErrorResponse {
    Date timestamp;
    String message;
    String details;
}



