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
public class OrderProductDto {
    private UUID productId;
    private int amount;

    public static OrderProductDto of(final OrderProductDto orderProductDto) {
        return OrderProductDto.builder()
                .productId(orderProductDto.getProductId())
                .amount(orderProductDto.getAmount())
                .build();
    }
}
