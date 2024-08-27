package com.sparta.delivery.order.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.order.dto.OrderRequestDto;
import com.sparta.delivery.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_order")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> productList;

    @Column(name = "order_type", length = 10, nullable = false)
    private String type;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;

    public static Order create(OrderRequestDto request, Store store) {
        return Order.builder()
                .store(store)
                .productList(new ArrayList<>())
                .type(request.getOrderType())
                .isHidden(false)
                .build();
    }

    // 주문에 상품 추가 메서드
    public void addProduct(OrderProduct product) {
        productList.add(product);
    }
}
