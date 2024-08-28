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
    @NotBlank(message="이름은 필수 입력입니다.")
    private String name;
    @Min(1000)
    private int price;
    private String description;
    @NotBlank(message="등록할 음식점 id은 필수 입력입니다.")
    private UUID StoreId;

}
