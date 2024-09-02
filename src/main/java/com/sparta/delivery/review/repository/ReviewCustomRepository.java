package com.sparta.delivery.review.repository;

import com.sparta.delivery.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReviewCustomRepository {

    Page<Review> findAllByConditionWithOwner(UUID storeId, String keyWord, String type, Pageable pageable);
}
