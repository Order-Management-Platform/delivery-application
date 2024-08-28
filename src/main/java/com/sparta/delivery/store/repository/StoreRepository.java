package com.sparta.delivery.store.repository;

import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
    List<Store> findAllByUser(User user);

    Page<Store> findByCategoryIdAndRegionIdAndNameContaining(UUID categoryId, UUID regionId, String name, Pageable pageable);
    Page<Store> findByRegionIdAndNameContaining(UUID regionId, String name, Pageable pageable);
}
