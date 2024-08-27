package com.sparta.delivery.store.dto;

import com.sparta.delivery.store.entity.Store;
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
    private String name;
    private String description;
    private String tel;
    private int minPrice;
    private String address;
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