package com.sparta.delivery.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductRequestDto {
    private UUID productId;
    private int price;
}
