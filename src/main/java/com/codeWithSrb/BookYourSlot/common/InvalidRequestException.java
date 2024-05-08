package com.codeWithSrb.BookYourSlot.common;

public class InvalidRequestException extends RuntimeException{

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, Throwable t) {
        super(message, t);
    }
}
