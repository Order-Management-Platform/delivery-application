package com.sparta.delivery.order.dto;

import com.sparta.delivery.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private UUID orderId;
    private UUID userId;
    private UUID storeId;
    private List<OrderProductDto> product;
    private String orderType;
    private int totalPrice;
    private String address;
    private String orderStatus;

    public static OrderResponseDto of(
            final Order order,
            final List<OrderProductDto> products,
            final int totalPrice
    ) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .storeId(order.getStore().getId())
                .product(products)
                .orderType(order.getType())
                .totalPrice(totalPrice)
                .address(order.getStore().getAddress())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
