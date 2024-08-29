package com.sparta.delivery.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    ORDER_CREATED("주문 생성"),
    PAYMENT_COMPLETED("결제 완료"),
    PAYMENT_FAILED("결제 실패"),
    PREPARING_FOR_SHIPMENT("배송 준비 중"),
    SHIPPED("배송 중"),
    DELIVERED("배송 완료"),
    CANCELLED("주문 취소"),
    REFUNDED("환불 완료");

    private final String description;

    public String getDescription() {
        return description;
    }
}

