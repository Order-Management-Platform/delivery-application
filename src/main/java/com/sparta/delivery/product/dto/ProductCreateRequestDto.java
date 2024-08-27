package com.sparta.delivery.product.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDto {
    private String name;
    private int price;
    private String description;
    private UUID StoreId;

    /*public Product toEntity(ProductCreateRequestDto dto) {
        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .store(dto.storeId())
                .build();
    }*/
}
