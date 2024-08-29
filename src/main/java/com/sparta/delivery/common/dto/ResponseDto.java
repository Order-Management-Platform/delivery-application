package com.sparta.delivery.common.dto;

import com.sparta.delivery.common.ResponseCode;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ResponseDto {
    private int status;
    private String message;

    public static ResponseDto of(final int status, final String message) {
        return ResponseDto.builder()
                .status(status)
                .message(message)
                .build();
    }

    public static ResponseDto of(ResponseCode resCode) {
        return ResponseDto.builder()
                .status(resCode.getStatus())
                .message(resCode.getMessage())
                .build();
    }
}