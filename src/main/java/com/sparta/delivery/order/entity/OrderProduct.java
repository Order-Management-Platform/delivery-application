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
    @Column(name = "order_product_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    public static OrderProduct create(
            final Order order,
            final UUID productId,
            final int price,
            final int amount
    ) {
        return OrderProduct.builder()
                .order(order)
                .productId(productId)
                .price(price)
                .amount(amount)
                .build();
    }
}
