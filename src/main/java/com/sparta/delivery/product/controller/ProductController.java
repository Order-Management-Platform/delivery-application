package com.sparta.delivery.product.controller;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.product.dto.ProductCreateRequestDto;
import com.sparta.delivery.product.dto.ProductListResponseDto;
import com.sparta.delivery.product.dto.ProductModifyRequestDto;
import com.sparta.delivery.product.dto.ProductResponseDto;
import com.sparta.delivery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 음식점 내 상품 추가
     * @param storeId   음식점 식별자
     * @param dto   상품 정보 dto
     * 리소스 접근 사용자와 음식점 생성자가 동일한지 검사합니다.
     */
    @PreAuthorize("(hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)) or hasRole('MANAGER')")
    @PostMapping("/{storeId}")
    public ResponseEntity<ResponseDto> createProduct(@PathVariable UUID storeId,@RequestBody ProductCreateRequestDto dto) {
        productService.createProduct(dto,storeId);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_PRODUCT_CREATE));
    }

    /**
     * 음식점 내 상품 목록 조회
     * @param storeId   음식점 식별자
     * @param keyWord   키워드
     * @param page      조회 페이지
     * @param size      조회 페이지 사이즈
     * @param asc       정렬 방샹
     * @param sort      정렬 기준
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<ResponsePageDto<ProductListResponseDto>> getStoreProductList(@PathVariable UUID storeId,
                                                            @RequestParam(required = false,defaultValue="") String keyWord,
                                                            @RequestParam(required = false, defaultValue = "1") int page,
                                                            @RequestParam(required = false, defaultValue = "10") int size,
                                                            @RequestParam(required = false, defaultValue = "false") boolean asc,
                                                            @RequestParam(required = false, defaultValue = "createdAt") String sort
    ) {
        Pageable pageable = asc ? PageRequest.of(page-1, size, Sort.by(sort).ascending()) :
                PageRequest.of(page-1, size, Sort.by(sort).descending());
        Page<ProductListResponseDto> data = productService.getStoreProductList(storeId, keyWord, pageable);

        return ResponseEntity.ok(ResponsePageDto.of(ResponseCode.SUCC_PRODUCT_LIST_GET, data));
    }

    /**
     * 상품 상세조회
     * @param productId 상품 식별자
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{productId}")
    public ResponseEntity<ResponseSingleDto<ProductResponseDto>> getProduct(@PathVariable UUID productId) {
        ProductResponseDto data=productService.getProduct(productId);
        return ResponseEntity.ok(ResponseSingleDto.of(ResponseCode.SUCC_PRODUCT_GET, data));
    }

    /**
     * 상품 수정
     * @param productId 상품 식별자
     * @param dto       상품 정보 dto
     * 리소스 접근 사용자와 상품의 음식점 생성자가 동일한지 검사
     */
    @PreAuthorize("(hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)) or hasRole('MANAGER')")
    @PutMapping("/{productId}")
    public ResponseEntity<ResponseDto> ModifyProduct(@PathVariable UUID productId,
                                            @RequestBody ProductModifyRequestDto dto) {
        productService.modifyProduct(productId, dto);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_PRODUCT_MODIFY);
        return ResponseEntity.ok(response);
    }

    /**
     * 상품 상태 변경
     * @param productId 상품 식별자
     * 리소스 접근 사용자와 상품의 음식점 생성자가 동일한지 검사
     */
    @PreAuthorize("(hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)) or hasRole('MANAGER')")
    @PatchMapping("/{productId}")
    public ResponseEntity<ResponseDto> switchProductStatus(@PathVariable UUID productId) {
        productService.modifyProductStatus(productId);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_PRODUCT_MODIFY));
    }

    /**
     * 상품 삭제
     * @param productId 상품 식별자
     * 리소스 접근 사용자와 상품의 음식점 생성자가 동일한지 검사
     */
    @PreAuthorize("(hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)) or hasRole('MANAGER')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ResponseDto> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_PRODUCT_DELETE));
    }
}