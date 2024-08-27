package com.sparta.delivery.store.service;

import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import com.sparta.delivery.store.dto.StoreGetResponseDto;
import com.sparta.delivery.store.dto.StoreModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    public StoreGetResponseDto getStore(UUID storeId) {
        Store store = storeRepository.findById(storeId).get();
        return new StoreGetResponseDto(store);
    }

    public void modifyStore(UUID storeId, StoreModifyRequestDto dto) {
        Store store = storeRepository.findById(storeId).get();
        Category category = categoryRepository.findById(dto.getCategory()).get();
        Store ModifyStore=Store.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .tel(dto.getTel())
                .minPrice(dto.getMinPrice())
                .address(dto.getAddress())
                .operatingTime(dto.getOperatingTime())
                .category(category)
                .build();
        storeRepository.save(ModifyStore);
    }

    public void deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId).get();
        storeRepository.delete(store);
    }

}
