package com.sparta.delivery.store.repository;

import com.sparta.delivery.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StoreCustomRepository {
    Page<Store> findAllByCondition(UUID regionId, UUID categoryId, String keyWord, Pageable pageable);

}
