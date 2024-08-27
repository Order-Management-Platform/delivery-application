package com.sparta.delivery.order.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.order.dto.OrderRequestDto;
import com.sparta.delivery.payment.entity.Payment;
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

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> productList;

    @Column(name = "order_type", length = 10, nullable = false)
    private String type;

    @Column(name = "is_hidden", nullable = false)
    private boolean isHidden;

    @Column(name = "order_status")
    private String orderStatus;

    public static Order create(OrderRequestDto request, Store store) {
        return Order.builder()
                .store(store)
                .productList(new ArrayList<>())
                .type(request.getOrderType())
                .isHidden(false)
                .orderStatus("결제 요청 중")
                .build();
    }

    // 주문에 상품 추가 메서드
    public void addProduct(OrderProduct product) {
        productList.add(product);
    }
}
