package com.pgs.booking.exception;

public class IllegalUserException extends RuntimeException{
    private static final long serialVersionUID = 2L;
    public IllegalUserException(String message){
        super(message);
    }
}
