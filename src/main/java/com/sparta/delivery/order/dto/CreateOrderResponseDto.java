package com.sparta.delivery.order.dto;

import com.sparta.delivery.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderResponseDto {
    private int status;
    private String message;
    private UUID orderId;

    // 삭제 예정
    public static CreateOrderResponseDto of(final int status, final String message, final UUID orderId) {
        return CreateOrderResponseDto.builder()
                .status(status)
                .message(message)
                .orderId(orderId)
                .build();
    }

    public static CreateOrderResponseDto of(ResponseCode responseCode, UUID orderId) {
        return CreateOrderResponseDto.builder()
                .status(responseCode.getStatus())
                .message(responseCode.getMessage())
                .orderId(orderId)
                .build();
    }
}
