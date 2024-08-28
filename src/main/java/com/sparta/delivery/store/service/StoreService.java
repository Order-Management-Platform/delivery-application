package com.sparta.delivery.store.service;

import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import com.sparta.delivery.store.dto.StoreGetResponseDto;
import com.sparta.delivery.store.dto.StoreListResponseDto;
import com.sparta.delivery.store.dto.StoreModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    //모든 리스트 조회
    //todo : query dsl로 변경
    public Page<StoreListResponseDto> getStoreList(UUID categoryId, UUID regionId, String keyWord, Pageable pageable) {
        Page<Store> storeList=categoryId.equals(null)
                ? storeRepository.findByRegionIdAndNameContaining(regionId,keyWord,pageable) :
                storeRepository.findByCategoryIdAndRegionIdAndNameContaining(categoryId,regionId,keyWord,pageable);
        return storeList.map(StoreListResponseDto::of);
    }


    //상세조회
    public StoreGetResponseDto getStore(UUID storeId) {
        Store store = storeRepository.findById(storeId).get();
        return StoreGetResponseDto.of(store);
    }

    //사장님 조회
    public List<Store> getOwnerStoreList(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).get();
        return storeRepository.findAllByUser(user);
    }

    //수정
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

    //삭제
    public void deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId).get();
        storeRepository.delete(store);
    }

}
