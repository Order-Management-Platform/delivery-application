package com.sparta.delivery.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.delivery.common.enums.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDto {

    private final int status;
    private final String errorMessage;

    public static ErrorResponseDto of(final ErrorCode errorCode) {
        return ErrorResponseDto.builder()
                .status(errorCode.getStatus())
                .errorMessage(errorCode.getMessage())
                .build();
    }

}
