package com.sparta.delivery.store.dto;

import com.sparta.delivery.store.entity.Store;
import lombok.*;

import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreGetResponseDto {
    private UUID storeId;
    private String name;
    private int minPrice;
    private String tel;
    private String description;
    private String operatingTime;
    private String address;

    public static StoreGetResponseDto of(Store store) {

        return StoreGetResponseDto.builder()
                .storeId(store.getId())
                .name(store.getName())
                .minPrice(store.getMinPrice())
                .tel(store.getTel())
                .description(store.getDescription())
                .operatingTime(store.getOperatingTime())
                .address(store.getAddress())
                .build();
    }
}
