package com.sparta.delivery.store.repository;

import com.sparta.delivery.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, UUID> {
}
