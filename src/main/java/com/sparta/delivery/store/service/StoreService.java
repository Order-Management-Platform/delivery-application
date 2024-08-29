package com.sparta.delivery.store.service;

import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.region.entity.Region;
import com.sparta.delivery.region.repository.RegionRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;


    public void createStore(UUID userId, Map<String,String> map) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException("사용자를 찾을 수 없습니다."));

        Store store = Store.builder()
                .name(map.get("name"))
                .user(user)
                .build();
        storeRepository.save(store);
    }

    //todo : query dsl로 변경
    public Page<StoreListResponseDto> getStoreList(UUID categoryId, UUID regionId, String keyWord, Pageable pageable) {
        Page<Store> storeList= categoryId == null
                ? storeRepository.findAllByRegionIdAndNameContaining(regionId,keyWord,pageable) :
                storeRepository.findAllByCategoryIdAndRegionIdAndNameContaining(categoryId,regionId,keyWord,pageable);
        return storeList.map(StoreListResponseDto::of);
    }

    public StoreGetResponseDto getStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new NotFoundException(ResponseCode.NOT_FOUND_STORE.getMessage()));
        return StoreGetResponseDto.of(store);
    }

    @Transactional
    public List<Store> getOwnerStoreList(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("사용자를 찾을 수 없습니다."));
        return storeRepository.findAllByUser(user);
    }

    //todo : 생성자 구조 제네릭으로 수정
    public void modifyStore(UUID storeId, StoreModifyRequestDto dto) {
        storeRepository.findById(storeId)
                .orElseThrow(()->new NotFoundException(ResponseCode.NOT_FOUND_STORE.getMessage()));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()->new NotFoundException("카테고리를 찾을 수 없습니다."));
        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(()->new NotFoundException("지역을 찾을 수 없습니다. "));

        Store ModifyStore=Store.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .tel(dto.getTel())
                .minPrice(dto.getMinPrice())
                .address(dto.getAddress())
                .operatingTime(dto.getOperatingTime())
                .category(category)
                .region(region)
                .build();
        storeRepository.save(ModifyStore);
    }

    public void deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new NotFoundException(ResponseCode.NOT_FOUND_STORE.getMessage()));
        storeRepository.delete(store);
    }

}
