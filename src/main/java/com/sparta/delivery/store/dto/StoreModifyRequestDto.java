package com.sparta.delivery.store.dto;

import com.sparta.delivery.store.entity.Store;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreModifyRequestDto {
    @NotBlank(message="이름은 필수 입력입니다.")
    private String name;
    private String description;
    @Pattern(regexp = "[0-9]{3}-[0-9]{4}-[0-9]{4}",message = "전화번호 양식을 맞춰주세요")
    private String tel;
    @Min(1000)
    private int minPrice;
    private String address;
    @Pattern(regexp = "[0-9]{2}:[0-9]{2}~[0-9]{2}:[0-9]{2}",message = "운영시간 양식을 맞춰주세요")
    private String operatingTime;
    private UUID category;

    public Store toEntity(StoreModifyRequestDto dto) {
        return Store.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .tel(dto.getTel())
                .minPrice(dto.getMinPrice())
                .address(dto.getAddress())
                .operatingTime(dto.getOperatingTime())
                .build();
    }
}