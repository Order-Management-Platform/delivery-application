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
    private String name;
    private String description;
    private String tel;
    private int minPrice;
    private String address;
    private String operatingTime;
    private UUID categoryId;
    private UUID regionId;

}