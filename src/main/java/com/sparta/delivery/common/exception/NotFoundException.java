package com.sparta.delivery.common.exception;

public class NotFoundException extends IllegalArgumentException{
    public NotFoundException(String message) {
        super(message);
    }
}
