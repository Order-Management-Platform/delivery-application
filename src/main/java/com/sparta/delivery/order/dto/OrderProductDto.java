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
    private int price;
    private int amount;

    public static OrderProductDto of(final UUID productId, final int price, final int amount) {
        return OrderProductDto.builder()
                .productId(productId)
                .price(price)
                .amount(amount)
                .build();
    }
}
