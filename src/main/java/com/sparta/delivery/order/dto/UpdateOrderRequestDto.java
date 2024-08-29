package com.sparta.delivery.order.dto;

import com.sparta.delivery.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequestDto {
    private UUID orderId;
    private OrderStatus orderStatus;
}
