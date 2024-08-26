package com.sparta.delivery.order.dto;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private int status;
    private String message;

    public static OrderResponseDto of(final int status, final String message) {
        return OrderResponseDto.builder()
                .status(status)
                .message(message)
                .build();
    }
}
