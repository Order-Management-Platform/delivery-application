package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.ResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{

    private final ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
