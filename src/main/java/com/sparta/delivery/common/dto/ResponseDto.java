package com.sparta.delivery.common.dto;

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
}
