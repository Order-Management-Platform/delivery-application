package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.ResponseCode;

public class NotFoundException extends IllegalArgumentException{
    public NotFoundException(String message) {
        super(message);
    }

}
