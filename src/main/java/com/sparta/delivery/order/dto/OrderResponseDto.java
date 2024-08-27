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


    // 우선 하나씩 다 받고 이후에 리펙토링 필요할 듯. - kyeonkim
    public static OrderResponseDto of(
            final Order order,
            final UUID storeId,
            final List<OrderProductDto> products,
            final int totalPrice,
            final String address,
            final String orderStatus // 현재 Order에 컬럼이 없으므로 임시로 받아옴 - kyeonkim
    ) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .storeId(storeId)
                .product(products)
                .orderType(order.getType())
                .totalPrice(totalPrice)
                .address(address)
                .orderStatus(orderStatus)
                .build();
    }
}
