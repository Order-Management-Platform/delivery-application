package com.sparta.delivery.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private UUID storeId;
    private List<OrderProductDto> product;
    private String orderType;
}
