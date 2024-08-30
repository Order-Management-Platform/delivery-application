package com.sparta.delivery.review;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.review.dto.ReviewCreateRequestDto;
import com.sparta.delivery.review.dto.ReviewListResponseDto;
import com.sparta.delivery.review.dto.ReviewModifyRequestDto;
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
    public void createReview(ReviewCreateRequestDto dto, Principal principal) {
        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_STORE));
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_USER));

        Review review = Review.builder()
                .store(store)
                .user(user)
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();

        reviewRepository.save(review);
    }


    //사장님 리뷰 목록 조회
    public Page<ReviewListResponseDto> getOwnerReviewList(UUID storeId, String keyWord, String type, Pageable pageable) {
        //type - name content 에대한 검색어로 조회
        //ype=name일 경우, content일 경우
        Page<Review> reviewList = reviewRepository.findAllByStoreWithOwner(storeId, keyWord, type, pageable);
        return reviewList.map(ReviewListResponseDto::of);

    }
    //사용자 리뷰 목록 조회
    public Page<ReviewListResponseDto> getReviewList(UUID storeId,Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_STORE));
        Page<Review> review = reviewRepository.findAllByStore(store, pageable);
        return review.map(ReviewListResponseDto::of);
    }

    //entity에 서비스 로직이 들어가는게 맞을까
    public void modifyProduct(UUID reviewId, ReviewModifyRequestDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_REVIEW));
        review.modify(dto);
        reviewRepository.save(review);

    }

    public void deleteProduct(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_REVIEW));
        reviewRepository.delete(review);

    }

    //repository 메소드명 다시 생각
    @Transactional
    public void ReportReview(UUID reviewId, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_REVIEW));
        reviewRepository.modifyDeclaration(review.getId(), content);
    }
}
