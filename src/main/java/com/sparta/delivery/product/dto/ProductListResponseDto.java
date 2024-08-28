package com.sparta.delivery.product.dto;

import com.sparta.delivery.product.entity.Product;
import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.region.entity.Region;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListResponseDto {

    private UUID storeId;
    private String name;
    private int price;
    private String description;
    private Boolean soldOut;

    public static ProductListResponseDto of(Product product) {
        return ProductListResponseDto.builder()
                .storeId(product.getStore().getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .soldOut(product.getSoldOut())
                .build();
    }
}
