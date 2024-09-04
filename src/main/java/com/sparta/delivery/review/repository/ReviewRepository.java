package com.sparta.delivery.review.repository;

import com.sparta.delivery.review.entity.Review;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID>, ReviewCustomRepository {

    List<Review> findByUser(User user);

    Page<Review> findAllByStore(Store store, Pageable pageable);


}
