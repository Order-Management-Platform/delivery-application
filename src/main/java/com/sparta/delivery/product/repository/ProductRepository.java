package com.sparta.delivery.product.repository;

import com.sparta.delivery.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByStoreId(UUID storeId, Pageable pageable);
    List<Product> findAllByStoreId(UUID storeId);
}
