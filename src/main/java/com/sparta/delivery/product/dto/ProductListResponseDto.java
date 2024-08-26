package com.sparta.delivery.product.dto;

import com.sparta.delivery.product.entity.Product;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListResponseDto {

    private UUID storeId;
    private String name;
    private int price;
    private String description;
    private Boolean soldOut;

    public ProductListResponseDto(Product product) {
        this.storeId = product.getStore().getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.soldOut = product.getSoldOut();
    }
}
