package com.sparta.delivery.payment.dto;

import com.sparta.delivery.payment.entity.PaymentStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PaymentRequest {

    private UUID userId;
    private UUID orderId;
    private Long paymentAmount;
    private PaymentStatus paymentStatus;
    private String pgTransactionId;


}
