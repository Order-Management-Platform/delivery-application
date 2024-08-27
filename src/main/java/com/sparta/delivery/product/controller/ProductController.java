package com.sparta.delivery.product.controller;

import com.sparta.delivery.product.dto.ProductCreateRequestDto;
import com.sparta.delivery.product.dto.ProductListResponseDto;
import com.sparta.delivery.product.dto.ProductModifyRequestDto;
import com.sparta.delivery.product.dto.ProductResponseDto;
import com.sparta.delivery.product.entity.Product;
import com.sparta.delivery.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public void createProduct(@RequestBody ProductCreateRequestDto dto) {
        productService.createProduct(dto);
    }

    @GetMapping("/store/{storeId}")
    public Page<ProductListResponseDto> getStoreProductList(@PathVariable UUID storeId,
                                                            @RequestParam(required = false,defaultValue="") String keyWord,
                                                            @RequestParam(required = false, defaultValue = "1") int page,
                                                            @RequestParam(required = false, defaultValue = "10") int size,
                                                            @RequestParam(required = false, defaultValue = "false") boolean asc,
                                                            @RequestParam(required = false, defaultValue = "createdAt") String sort
    ) {
        Pageable pageable = asc ? PageRequest.of(page-1, size, Sort.by(sort).ascending()) :
                PageRequest.of(page-1, size, Sort.by(sort).descending());
        return productService.getStoreProductList(storeId,keyWord, pageable);
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProduct(@PathVariable UUID productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("/{productId}")
    public void modifyProduct(@PathVariable UUID productId,
                              @RequestBody ProductModifyRequestDto dto) {
        productService.modifyProduct(productId, dto);
    }


    @PatchMapping("/{productId}")
    public void switchProductStatus(@PathVariable UUID productId) {
        productService.modifyProductStatus(productId);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
    }
}