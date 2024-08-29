package com.sparta.delivery.product.dto;

import com.sparta.delivery.product.entity.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private UUID id;
    private String name;
    private int price;
    private String description;
    private UUID storeId;
    private LocalDateTime createdAt;

    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .storeId(product.getStore().getId())
                .createdAt(product.getCreatedAt())
                .build();
    }
}


