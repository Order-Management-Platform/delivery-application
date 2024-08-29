package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.enums.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends IllegalArgumentException{

    private ErrorCode errorCode;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

