package com.sparta.delivery.payment.dto;

import com.sparta.delivery.payment.entity.Payment;
import com.sparta.delivery.payment.entity.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentInfoResponse {

    private UUID paymentId;
    private UUID userId;
    private Long paymentAmount;
    private PaymentStatus paymentStatus;
    private String pgTransactionId;
    private LocalDateTime createdAt;

    public static PaymentInfoResponse of(Payment payment) {
        return PaymentInfoResponse.builder()
                .paymentId(payment.getId())
                .userId(payment.getUser().getId())
                .paymentAmount(payment.getPaymentAmount())
                .paymentStatus(payment.getStatus())
                .pgTransactionId(payment.getPgTransactionId())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
