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
    private UUID storeId;
    private List<OrderProductDto> product;
    private String orderType;
    private int totalPrice;
    private String address;
    private String orderStatus;

    public static OrderResponseDto of(
            final Order order,
            final UUID storeId,
            final List<OrderProductDto> products,
            final int totalPrice,
            final String address
    ) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .storeId(storeId)
                .product(products)
                .orderType(order.getType())
                .totalPrice(totalPrice)
                .address(address)
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
