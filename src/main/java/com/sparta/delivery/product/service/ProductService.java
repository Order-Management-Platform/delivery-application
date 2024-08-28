package com.sparta.delivery.product.service;

import com.sparta.delivery.product.dto.ProductCreateRequestDto;
import com.sparta.delivery.product.dto.ProductListResponseDto;
import com.sparta.delivery.product.dto.ProductModifyRequestDto;
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
import org.springframework.transaction.annotation.Transactional;

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
    /*
    public  Page<ProductListResponseDto> getStoreProductList(UUID storeId,String keyWord, Pageable pageable) {

        Page<Product> product = productRepository.findAllByStoreIdAndNameContaining(storeId,keyWord, pageable);
        return product.map(ProductListResponseDto::of);
    }
   */

    //상품 상세 조회
    public ProductResponseDto getProduct(UUID productId) {
        Product product = productRepository.findById(productId).get();
        return ProductResponseDto.of(product);
    }

    //상품 수정
    @Transactional
    public void modifyProduct(UUID productId, ProductModifyRequestDto dto) {
        Product product = productRepository.findById(productId).get();
        product.modify(dto);
        productRepository.save(product);
    }

    //상품 상태
    /*
    @Transactional
    public void modifyProductStatus(UUID productId) {
        Product product = productRepository.findById(productId).get();
        productRepository.modifyStatus(productId,!product.getSoldOut());
    }
     */

    //상품 삭제
    @Transactional
    public void deleteProduct(UUID productId) {
        Product product=productRepository.findById(productId).get();
        productRepository.delete(product);
    }

}