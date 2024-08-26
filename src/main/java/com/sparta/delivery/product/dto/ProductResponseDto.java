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
    private UUID store;
    private LocalDateTime createdAt;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.name=product.getName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.store = product.getStore().getId();
        this.createdAt = product.getCreatedAt();
    }
}

//todo : 정적 팩토리 메서드
/*public static ProductResponseDto from(Product product){
    return new ProductResponseDto(product);
}*/
