package com.sparta.delivery.store.service;

import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;


    //음식점 생성
    public void createStore(UUID userId, Map<String,String> map) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BusinessException(ResponseCode.NOT_FOUND_USER));

        Store store = Store.builder()
                .name(map.get("name"))
                .user(user)
                .build();
        storeRepository.save(store);
    }

    // 음식점 사용자 조회
    public Page<StoreListResponseDto> getStoreList(UUID categoryId, UUID regionId, String keyWord, Pageable pageable) {
        Page<Store> storeList = storeRepository.findAllByCondition(regionId,categoryId,keyWord,pageable);
        return storeList.map(StoreListResponseDto::of);
    }

    //음식점 사장님 조회
    @Transactional
    public List<StoreListResponseDto> getOwnerStoreList(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new BusinessException(ResponseCode.NOT_FOUND_USER));
        List<Store> storelist=storeRepository.findAllByUser(user);
        return storelist.stream().map(StoreListResponseDto::of)
                .collect(Collectors.toList());
    }

    //음식점 상세 조회
    public StoreGetResponseDto getStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new BusinessException(ResponseCode.NOT_FOUND_STORE));
        return StoreGetResponseDto.of(store);
    }

    //음식점 수정
    //todo : 생성자 구조 제네릭으로 수정
    @Transactional
    public void modifyStore(UUID storeId, StoreModifyRequestDto dto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_STORE));
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(()->new BusinessException(ResponseCode.NOT_FOUND_CATEGORY));
        Region region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(()->new BusinessException(ResponseCode.NOT_FOUND_REGiON));

        store.modify(dto, category, region);
//        storeRepository.save(store);
    }

    //음식점 삭제
    public void deleteStore(UUID storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_STORE));
        store.delete();
    }

}
