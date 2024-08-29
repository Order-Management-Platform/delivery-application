package com.sparta.delivery.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

}
