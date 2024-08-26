package com.sparta.delivery.product.service;

import com.sparta.delivery.product.dto.ProductCreateRequestDto;
import com.sparta.delivery.product.dto.ProductListResponseDto;
import com.sparta.delivery.product.dto.ProductResponseDto;
import com.sparta.delivery.product.entity.Product;
import com.sparta.delivery.product.repository.ProductRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    //상품 생성
    public void createProduct(ProductCreateRequestDto dto) {

        Store store = storeRepository.findById(dto.getStoreId()).get();

        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .store(store)
                .build();
        productRepository.save(product);
    }


    //가게 내 상품 조회
    public  Page<ProductListResponseDto> getStoreProductList(UUID storeId, Pageable pageable) {
        Page<Product> product = productRepository.findAllByStoreId(storeId, pageable);
        List<ProductListResponseDto> list=product.stream()
            .map(ProductListResponseDto::new)
            .collect(Collectors.toList());

        return new PageImpl<>(list, pageable, product.getTotalElements());

    }

    //상품 상세 조회
    public ProductResponseDto getProduct(UUID productId) {
        Product product = productRepository.findById(productId).get();
        return new ProductResponseDto(product);
    }

}
