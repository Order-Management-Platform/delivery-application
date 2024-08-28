package com.sparta.delivery.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "p_order_product")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_product_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "amount")
    private int amount;

    public static OrderProduct create(final Order order, final UUID productId, final int amount) {
        return OrderProduct.builder()
                .order(order)
                .productId(productId)
                .amount(amount)
                .build();
    }
}
