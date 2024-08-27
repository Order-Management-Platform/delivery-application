package com.sparta.delivery.product.repository;

import com.sparta.delivery.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.rmi.server.UID;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findAllByStoreIdAndNameContaining(UUID storeId, String name, Pageable pageable);
    List<Product> findAllByStoreId(UUID storeId);

    @Modifying
    @Query(value = "UPDATE Product SET soldOut=:status WHERE id=:productId")
    void modifyStatus(@Param("productId") UUID productId, @Param("status") boolean status);
}
