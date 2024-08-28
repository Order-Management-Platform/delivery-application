package com.sparta.delivery.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductModifyRequestDto {
    @NotBlank(message="이름은 필수 입력입니다.")
    private String name;
    @Min(1000)
    private int price;
    private String description;
}