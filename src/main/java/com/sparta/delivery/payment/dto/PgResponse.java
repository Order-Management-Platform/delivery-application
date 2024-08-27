package com.sparta.delivery.payment.dto;

import lombok.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PgResponse {

    private String pgTransactionId;
    private int statusCode;
    private String message;

    public static PgResponse create (String pgTransactionId) {
        return PgResponse.builder()
                .pgTransactionId(pgTransactionId)
                .statusCode(200)
                .message("결제 성공").build();
    }
}
