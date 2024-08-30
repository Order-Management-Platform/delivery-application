package com.sparta.delivery.review;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.product.dto.ProductCreateRequestDto;
import com.sparta.delivery.product.dto.ProductListResponseDto;
import com.sparta.delivery.product.dto.ProductModifyRequestDto;
import com.sparta.delivery.product.dto.ProductResponseDto;
import com.sparta.delivery.review.dto.ReviewCreateRequestDto;
import com.sparta.delivery.review.dto.ReviewListResponseDto;
import com.sparta.delivery.review.dto.ReviewModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseDto createReview(@RequestBody ReviewCreateRequestDto dto, Principal principal) {
        reviewService.createReview(dto,principal);
        return ResponseDto.of(ResponseCode.SUCC_REVIEW_CREAET);
    }

    /**
     * 리뷰 사용자 목록조회
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{storeId}")
    public ResponsePageDto getReviewList(@PathVariable UUID storeId,
                                         @RequestParam(required = false, defaultValue = "1") int page,
                                          @RequestParam(required = false, defaultValue = "10") int size) {

        Pageable pageable=PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        Page<ReviewListResponseDto> data = reviewService.getReviewList(storeId,pageable);
        return ResponsePageDto.of(ResponseCode.SUCC_REVIEW_USER_LIST_GET, data);
    }

    /**
     * 리뷰 사장님 목록조회
     */
    @PreAuthorize("hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId) ")
    @GetMapping("/owner/{storeId}")
    public ResponsePageDto getOwnerReviewList(@PathVariable UUID storeId,
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
        return ResponsePageDto.of(ResponseCode.SUCC_REVIEW_LIST_GET, data);
    }

    /**
     * 리뷰 수정
     * 해당 리뷰의 생성자일 경우
     */
    @PreAuthorize("hasRole('CUSTOMER') and @securityUtil.isReivewOwner(authentication,#reviewId)")
    @PutMapping("/{reviewId}")
    public ResponseDto ModifyReview(@PathVariable UUID reviewId,
                                    @RequestBody ReviewModifyRequestDto dto) {
        reviewService.modifyProduct(reviewId, dto);
        return ResponseDto.of(ResponseCode.SUCC_REVIEW_MODIFY);
    }

    /**
     * 리뷰 삭제
     * 해당 리뷰의 생성자일 경우
     */
    @PreAuthorize("hasRole('CUSTOMER') and @securityUtil.isReivewOwner(authentication,#reviewId)")
    @DeleteMapping("/{reviewId}")
    public ResponseDto deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteProduct(reviewId);
        return ResponseDto.of(ResponseCode.SUCC_REVIEW_DELETE);
    }

    /**
     * 리뷰 신고
     */
    //content를 body로? 쿼리 스트링으로? -> dto 유효성 검사
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/{reviewId}")
    public ResponseDto ReportReview(@PathVariable UUID reviewId,
                                    @RequestParam(required = true) String content) {
        reviewService.ReportReview(reviewId,content);
        return ResponseDto.of(ResponseCode.SUCC_REVIEW_REPORT);
    }


}
