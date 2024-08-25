package com.sparta.delivery.order.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.order.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_order")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id", length = 100, nullable = false)
    private UUID id;

    @Column(name = "store_name")
    private String storeName;

    /*
    상품 엔티티가 필요. 우선 빼고 진행 - kyeonkim
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    */

    @Column(name = "order_type", length = 10, nullable = false)
    private String type;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;

    public static Order create(OrderRequestDto request) {
        return Order.builder()
                .storeName(request.getStoreName())
                .type(request.getOrderType())
                // Product 추가 필요 - kyeonkim
                .isHidden(false)
                .build();
    }
}
