package com.sparta.delivery.payment.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.payment.dto.PaymentRequest;
import com.sparta.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "p_payment")
@SQLRestriction("deleted_at is null")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 오더 연관관계 일대일
    //@OneToOne
    //private Order order;

    private Long paymentAmount;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    private String pgTransactionId;

    private LocalDateTime paymentAt;
    private UUID paymentBy;

    public static Payment toEntity(PaymentRequest paymentRequest, User user /*, Order order*/) {
        return Payment.builder()
                .user(user)
                //.order(order)
                .status(paymentRequest.getPaymentStatus())
                .paymentAmount(paymentRequest.getPaymentAmount())
                .paymentAt(LocalDateTime.now())
                .paymentBy(paymentRequest.getUserId())
                .pgTransactionId(paymentRequest.getPgTransactionId())
                .build();
    }
}
