package com.sparta.delivery.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.delivery.common.ResponseCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorsResponseDto<T> {

    private final int status;
    private final String errorMessage;
    private final List<T> errors;

    public static <T> ErrorsResponseDto<T> of(final ResponseCode errorCode, List<T> errorMessages) {
        return ErrorsResponseDto.<T>builder()
                .status(errorCode.getStatus())
                .errorMessage("검증 실패")
                .errors(errorMessages)
                .build();
    }

}

