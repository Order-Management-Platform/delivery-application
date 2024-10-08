package com.sparta.delivery.order.repository;

import com.sparta.delivery.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByUserId(UUID userId, Pageable pageable);

    Page<Order> findAllByStoreId(UUID storeId, Pageable pageable);
}
