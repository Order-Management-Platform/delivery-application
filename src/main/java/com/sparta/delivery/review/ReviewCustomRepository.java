package com.sparta.delivery.review;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ReviewCustomRepository {

    Page<Review> findAllByStoreWithOwner(UUID storeId, String keyWord, String type, Pageable pageable);
}
