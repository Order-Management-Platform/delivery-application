package com.sparta.delivery.review.controller;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.review.service.ReviewService;
import com.sparta.delivery.review.dto.ReviewCreateRequestDto;
import com.sparta.delivery.review.dto.ReviewListResponseDto;
import com.sparta.delivery.review.dto.ReviewModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 생성
     * @param dto           리뷰 정보 dto
     * @param principal     사용자 정보를 담고 있는 객체
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity createReview(@RequestBody ReviewCreateRequestDto dto, Principal principal) {
        reviewService.createReview(dto, principal);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_REVIEW_CREAET);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 사용자 목록조회
     * @param storeId   음식점 식별자
     * @param page      조회 페이지
     * @param size      조회 페이지 사이즈
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{storeId}")
    public ResponseEntity getReviewList(@PathVariable UUID storeId,
                                         @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable=PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<ReviewListResponseDto> data = reviewService.getReviewList(storeId,pageable);

        ResponsePageDto response = ResponsePageDto.of(ResponseCode.SUCC_REVIEW_USER_LIST_GET, data);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 사장님 목록조회
     * @param storeId   음식점 식별자
     * @param keyWord   검색어
     * @param type      검색어의 타입
     * @param page      조회 페이지
     * @param size      조회 페이지 사이즈
     * @param sort      정렬 기준
     * @param asc       정렬 방향
     */
    @PreAuthorize("(hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)) or hasRole('MANAGER') ")
    @GetMapping("/owner/{storeId}")
    public ResponseEntity getOwnerReviewList(@PathVariable UUID storeId,
                                              @RequestParam(required = false, defaultValue = "") String keyWord,
                                              @RequestParam(required = false, defaultValue = "content") String type,
                                              @RequestParam(required = false, defaultValue = "1") int page,
                                              @RequestParam(required = false, defaultValue = "10") int size,
                                              @RequestParam(required = false, defaultValue = "createdAt") String sort,
                                              @RequestParam(required = false, defaultValue = "false") boolean asc
    ) {
        Pageable pageable = asc ? PageRequest.of(page - 1, size, Sort.by(sort).ascending()) :
                PageRequest.of(page - 1, size, Sort.by(sort).descending());
        Page<ReviewListResponseDto> data = reviewService.getOwnerReviewList(storeId, keyWord, type, pageable);

        ResponsePageDto response = ResponsePageDto.of(ResponseCode.SUCC_REVIEW_LIST_GET, data);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 수정
     * @param reviewId  리뷰 식별자
     * @param dto       리뷰 수정 정보 dto
     * 리소스 접근 사용자와  리뷰 생성자가 동일한지 검사
     */
    @PreAuthorize("(hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)) or hasRole('MANAGER') ")
    @PutMapping("/{reviewId}")
    public ResponseEntity ModifyReview(@PathVariable UUID reviewId,
                                    @RequestBody ReviewModifyRequestDto dto) {
        reviewService.modifyreview(reviewId, dto);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_REVIEW_MODIFY);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 삭제
     * @param reviewId  리뷰 식별자
     * 리소스 접근 사용자와  리뷰 생성자가 동일한지 검사
     */
    @PreAuthorize("(hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)) or hasRole('MANAGER') ")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_REVIEW_DELETE);
        return ResponseEntity.ok(response);
    }

    /**
     * 리뷰 신고
     * @param reviewId  리뷰 식별자
     * @param content   리뷰 내용
     */
    //todo : content를 body로? 쿼리 스트링으로? -> dto 유효성 검사
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/{reviewId}")
    public ResponseEntity ReportReview(@PathVariable UUID reviewId,
                                    @RequestParam String content) {
        reviewService.ReportReview(reviewId,content);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_REVIEW_REPORT);
        return ResponseEntity.ok(response);
    }


}
