package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.ResponseCode;
import lombok.Getter;

@Getter
public class CustomBadRequestException extends IllegalArgumentException{
    private ResponseCode responseCode;

    public CustomBadRequestException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}