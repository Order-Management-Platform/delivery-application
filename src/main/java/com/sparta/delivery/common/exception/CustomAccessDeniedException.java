package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.ResponseCode;
import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

@Getter
public class CustomAccessDeniedException extends AccessDeniedException {

    private final ResponseCode responseCode;

    public CustomAccessDeniedException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
