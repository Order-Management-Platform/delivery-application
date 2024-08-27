package com.sparta.delivery.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private int status;
    private String message;
    private UUID orderId;

    public static OrderResponseDto of(final int status, final String message, final UUID orderId) {
        return OrderResponseDto.builder()
                .status(status)
                .message(message)
                .orderId(orderId)
                .build();
    }
}
