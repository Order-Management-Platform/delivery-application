package com.sparta.delivery.store.controller;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.store.dto.StoreGetResponseDto;
import com.sparta.delivery.store.dto.StoreListResponseDto;
import com.sparta.delivery.store.dto.StoreModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    //todo : 생성한 resource가 맞는지 확인하는 로직 추가
    private final StoreService storeService;

    //관리자 페이지 - 가게 생성
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{userId}")
    public ResponseDto createStore(@PathVariable UUID userId, @RequestBody Map<String, String> map) {
        storeService.createStore(userId, map);
        return ResponseDto.of(ResponseCode.SUCC_STORE_CREATE);
    }


    /**
     * 음식점 조회
     */
    //todo : request dto로 변경
    //todo : 조회 sql 리팩토링 필요 - keyword포함에 대해
    @GetMapping
    public ResponsePageDto getStoreList(@RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int size,
                                                   @RequestParam(required = false, defaultValue = "false") boolean asc,
                                                   //@RequestParam(required = false, defaultValue = "name") String sort,
                                                   @RequestParam(required = false, defaultValue="") String keyWord,
                                                   @RequestParam(required = false,defaultValue="") UUID categoryId,
                                                   @RequestParam(required=true) UUID regionId
    ) {
        Pageable pageable = asc ? PageRequest.of(page-1, size, Sort.by("name").ascending()) :
                PageRequest.of(page-1, size, Sort.by("name").descending());
        Page<StoreListResponseDto> data = storeService.getStoreList(categoryId, regionId, keyWord, pageable);
        return ResponsePageDto.of(ResponseCode.SUCC_STORE_LIST_GET, data);
    }

    /**
     * 음식점 사장님 조회
     */
    //owener - 지역 파라미터 필수 , owner도 getStoreList()메서드에 접근 가능
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/ownerList")
    public ResponseSingleDto getStoreOwnerList(Principal principal){
        List<Store> data=storeService.getOwnerStoreList(principal);
        return ResponseSingleDto.of(ResponseCode.SUCC_STORE_OWNER_LIST_GET, data);

    }

    /**
     *  음식점 상세조회
     */
    @GetMapping("/{storeId}")
    public ResponseSingleDto getStore(@PathVariable UUID storeId) {
        StoreGetResponseDto data= storeService.getStore(storeId);
        return ResponseSingleDto.of(ResponseCode.SUCC_STORE_GET, data);
    }

    /**
     * 음식점 수정
     */
    @PreAuthorize("hasRole('OWNER') and @sunmiSecurityUtil.isStoreOwner(authentication,#storeId)")
    @PutMapping("/{storeId}")
    public ResponseDto ModifyStore(@PathVariable UUID storeId, @RequestBody StoreModifyRequestDto dto) {
        storeService.modifyStore(storeId, dto);
        return ResponseDto.of(ResponseCode.SUCC_STORE_MODIFY);
    }

    /**
     * 음식점 삭제
     */
    @PreAuthorize("hasRole('OWNER') and @sunmiSecurityUtil.isStoreOwner(authentication,#storeId)")
    @DeleteMapping("/{storeId}")
    public ResponseDto deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
        return ResponseDto.of(ResponseCode.SUCC_STORE_DELETE);
    }
}