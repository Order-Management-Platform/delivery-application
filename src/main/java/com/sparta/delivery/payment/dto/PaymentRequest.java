package com.sparta.delivery.payment.dto;

import lombok.Getter;

import java.util.UUID;


@Getter
public class PaymentRequest {

    private UUID orderId;
}
