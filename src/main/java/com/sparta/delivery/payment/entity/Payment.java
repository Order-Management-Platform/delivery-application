package com.sparta.delivery.payment.entity;

import com.sparta.delivery.common.BaseEntity;
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
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 오더 연관관계 일대일
    //@OneToOne
    //private Order order;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    private String pgTransactionId;

    private LocalDateTime paymentAt;
    private UUID paymentBy;

}
