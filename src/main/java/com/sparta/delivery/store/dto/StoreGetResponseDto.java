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
public class StoreGetResponseDto {
    private UUID storeId;
    private String name;
    private String description;
    private String tel;
    private int minPrice;
    private String address;
    private String operatingTime;

    public StoreGetResponseDto(Store store) {
        this.storeId = store.getId();
        this.name = store.getName();
        this.description = store.getDescription();
        this.tel = store.getTel();
        this.minPrice = store.getMinPrice();
        this.address = store.getAddress();
        this.operatingTime = store.getOperatingTime();
    }
}