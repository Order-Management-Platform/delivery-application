package com.sparta.delivery.payment.service;

import com.sparta.delivery.payment.dto.PaymentRequest;
import com.sparta.delivery.payment.dto.PgResponse;

import java.util.UUID;

public interface PaymentGatewayService {

    PgResponse paymentByCallback(UUID userId, PaymentRequest request);
    void cancelPayment(UUID orderId);
}
