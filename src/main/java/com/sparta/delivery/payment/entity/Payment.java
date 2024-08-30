package com.sparta.delivery.payment.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

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
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    private UUID orderId;


    private Long paymentAmount;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    private String pgTransactionId;


    public void changePaymentByFail(PaymentStatus paymentStatus) {
        this.status = paymentStatus;
    }

    public void changePaymentBySuccess(PaymentStatus paymentStatus, String pgTransactionId) {
        this.status = paymentStatus;
        this.pgTransactionId = pgTransactionId;
    }

    public void changePaymentByCancel(PaymentStatus cancelStatus) {
        this.status = cancelStatus;
    }

    public void delete(UUID userId) {
        this.markDeleted(userId);
    }

    public static Payment create(User user,UUID orderId, Long paymentAmount) {
        return Payment.builder()
                .orderId(orderId)
                .user(user)
                .paymentAmount(paymentAmount).build();
    }
}
