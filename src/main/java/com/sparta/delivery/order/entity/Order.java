package com.sparta.delivery.order.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.order.dto.OrderRequestDto;
import com.sparta.delivery.payment.entity.Payment;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_order")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SQLRestriction("deleted_at is null")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    public static Order create(OrderRequestDto request, User user, Store store) {
        return Order.builder()
                .user(user)
                .store(store)
                .productList(new ArrayList<>())
                .type(request.getOrderType())
                .isHidden(false)
                .orderStatus(OrderStatus.ORDER_CREATED)
                .build();
    }

    // 주문에 상품 추가 메서드
    public void addProduct(OrderProduct product) {
        productList.add(product);
    }

    // 주문 상태 수정 메서드
    public void updateStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    // 결제 완료 상태 메서드
    public void paymentCompleted() {
        this.orderStatus = OrderStatus.PAYMENT_COMPLETED;
    }

    // 결제 실패 상태 메서드
    public void paymentFailed() {
        this.orderStatus = OrderStatus.PAYMENT_FAILED;
    }

    // 주문 취소 메서드
    public void cancel(UUID orderId) {
        this.markDeleted(orderId);
    }

    //결제 엔티티 추가 메서드
    public void addPayment(Payment payment) {
        this.payment = payment;
    }
}
