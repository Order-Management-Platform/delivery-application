package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.ResponseCode;
import lombok.Getter;

@Getter
public class NotFoundException extends IllegalArgumentException{

    private ResponseCode responseCode;

    //삭제예정
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}

