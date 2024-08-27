package com.sparta.delivery.product.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductModifyRequestDto {
    private String name;
    private int price;
    private String description;
}