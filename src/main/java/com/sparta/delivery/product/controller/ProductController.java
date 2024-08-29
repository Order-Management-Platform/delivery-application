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
     * 음식점 상품 추가
     */
    @PreAuthorize("hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)")
    @PostMapping("/{storeId}")
    public ResponseDto createProduct(@PathVariable UUID storeId,@RequestBody ProductCreateRequestDto dto) {
        productService.createProduct(dto,storeId);
        return ResponseDto.of(ResponseCode.SUCC_PRODUCT_CREATE);
    }

    /**
     * 음식점 목록 조회
     */
    //쿼리스트링 dto로 매핑 , pageable service단에서
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/store/{storeId}")
    public ResponsePageDto getStoreProductList(@PathVariable UUID storeId,
                                                            @RequestParam(required = false,defaultValue="") String keyWord,
                                                            @RequestParam(required = false, defaultValue = "1") int page,
                                                            @RequestParam(required = false, defaultValue = "10") int size,
                                                            @RequestParam(required = false, defaultValue = "false") boolean asc,
                                                            @RequestParam(required = false, defaultValue = "createdAt") String sort
    ) {
        Pageable pageable = asc ? PageRequest.of(page-1, size, Sort.by(sort).ascending()) :
                PageRequest.of(page-1, size, Sort.by(sort).descending());
        Page<ProductListResponseDto> data = productService.getStoreProductList(storeId, keyWord, pageable);
        return ResponsePageDto.of(ResponseCode.SUCC_PRODUCT_LIST_GET, data);
    }

    /**
     * 상품 상세조회
     */
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{productId}")
    public ResponseSingleDto getProduct(@PathVariable UUID productId) {
        ProductResponseDto data=productService.getProduct(productId);
        return ResponseSingleDto.of(ResponseCode.SUCC_PRODUCT_GET,data);
    }

    /**
     *  상품 수정
     *  해당 상품 음식점의 사장일 경우
     */
    @PreAuthorize("hasRole('OWNER') and @securityUtil.isProductOwner(authentication,#productId)")
    @PutMapping("/{productId}")
    public ResponseDto ModifyProduct(@PathVariable UUID productId,
                                            @RequestBody ProductModifyRequestDto dto) {
        productService.modifyProduct(productId, dto);
        return ResponseDto.of(ResponseCode.SUCC_PRODUCT_MODIFY);
    }

    /**
     * 상품 상태 변경
     */
    @PreAuthorize("hasRole('OWNER') and @securityUtil.isProductOwner(authentication,#productId)")
    @PatchMapping("/{productId}")
    public ResponseDto switchProductStatus(@PathVariable UUID productId) {
        productService.modifyProductStatus(productId);
        return ResponseDto.of(ResponseCode.SUCC_PRODUCT_SWITCH_STATUS);
    }

    /**
     * 상품 삭제
     */
    @PreAuthorize("hasRole('OWNER') and @securityUtil.isProductOwner(authentication,#productId)")
    @DeleteMapping("/{productId}")
    public ResponseDto deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseDto.of(ResponseCode.SUCC_PRODUCT_DELETE);
    }
}