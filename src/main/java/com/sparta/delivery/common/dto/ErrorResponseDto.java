package com.sparta.delivery.common.dto;

import com.sparta.delivery.common.ResponseCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ErrorResponseDto {

    private final int status;
    private final String errorMessage;

    //삭제 예정
    public static ErrorResponseDto of(String errorMessage) {
        return ErrorResponseDto.builder()
                .status(400)
                .errorMessage(errorMessage)
                .build();
    }


    public static ErrorResponseDto of(final ResponseCode errorCode) {
        return ErrorResponseDto.builder()
                .status(errorCode.getStatus())
                .errorMessage(errorCode.getMessage())
                .build();
    }

}
