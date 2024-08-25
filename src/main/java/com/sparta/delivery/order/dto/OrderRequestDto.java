package com.sparta.delivery.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String storeName;
    //private List<Product> product; // Product Entity 필요. - kyeonkim
    private String orderType;
}
