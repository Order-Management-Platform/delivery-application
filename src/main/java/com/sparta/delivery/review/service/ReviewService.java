package com.sparta.delivery.review.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.review.entity.Review;
import com.sparta.delivery.review.dto.ReviewCreateRequestDto;
import com.sparta.delivery.review.dto.ReviewListResponseDto;
import com.sparta.delivery.review.dto.ReviewModifyRequestDto;
import com.sparta.delivery.review.repository.ReviewRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;


    //리뷰 생성
    @Transactional
    public void createReview(ReviewCreateRequestDto dto, Principal principal) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_STORE));
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_USER));
        //사용자 리뷰 생성
        Review review = Review.builder()
                .store(store)
                .user(user)
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();
        reviewRepository.save(review);

        //음식점의 평점 계산 후 반영
        store.ratingCalculation(dto.getRating());
        storeRepository.save(store);
    }

    //리뷰 사장님 목록 조회
    public Page<ReviewListResponseDto> getOwnerReviewList(UUID storeId, String keyWord, String type, Pageable pageable) {
        Page<Review> reviewList = reviewRepository.findAllByConditionWithOwner(storeId, keyWord, type, pageable);
        return reviewList.map(ReviewListResponseDto::of);

    }

    //리뷰 사용자 목록 조회
    @Transactional
    public Page<ReviewListResponseDto> getReviewList(UUID storeId,Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_STORE));
        Page<Review> review = reviewRepository.findAllByStore(store, pageable);
        return review.map(ReviewListResponseDto::of);
    }

    // 리뷰 수정
    //todo : entity에 서비스 로직이 들어가는게 맞을까
    @Transactional
    public void modifyreview(UUID reviewId, ReviewModifyRequestDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_REVIEW));
        review.modify(dto);
        reviewRepository.save(review);

    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_REVIEW));
        review.delete();
    }

    //리뷰 신고
    @Transactional
    public void ReportReview(UUID reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_REVIEW));

        review.declaration(content);
    }
}
