package com.sparta.delivery.payment.dto;

import com.sparta.delivery.payment.entity.PaymentStatus;
import lombok.Getter;

@Getter
public class CancelPaymentRequest {

    private PaymentStatus paymentStatus;

}
