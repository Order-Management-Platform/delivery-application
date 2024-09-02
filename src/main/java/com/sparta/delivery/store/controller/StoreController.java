package com.sparta.delivery.store.controller;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.store.dto.StoreGetResponseDto;
import com.sparta.delivery.store.dto.StoreListResponseDto;
import com.sparta.delivery.store.dto.StoreModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    //관리자 페이지 - 가게 생성
    //todo : test용 관리자 페이지에서 구현 필요
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{userId}")
    public ResponseEntity<ResponseDto> createStore(@PathVariable UUID userId, @RequestBody Map<String, String> map) {
        storeService.createStore(userId, map);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_STORE_CREATE);
        return ResponseEntity.ok(response);
    }

    /**
     * 음식점 사용자 조회
     * @param keyWord       검색어
     * @param page          조회 페이지
     * @param size          조회 페이지 사이즈
     * @param asc           정렬 방향
1    * @param sort          정렬 기준
     * @param categoryId    카테코리 식별자
     * @param regionId      지역 실벽자
     */
    @GetMapping
    public ResponseEntity<ResponsePageDto> getStoreList(@RequestParam(required = false, defaultValue="") String keyWord,
                                                        @RequestParam(required = false, defaultValue = "1") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size,
                                                        @RequestParam(required = false, defaultValue = "false") boolean asc,
                                                        @RequestParam(required = false, defaultValue = "name") String sort,
                                                        @RequestParam(required = false,defaultValue="") UUID categoryId,
                                                        @RequestParam(required=true) UUID regionId
    ) {
        Pageable pageable = asc ? PageRequest.of(page-1, size, Sort.by(sort).ascending()) :
                PageRequest.of(page-1, size, Sort.by(sort).descending());
        Page<StoreListResponseDto> data = storeService.getStoreList(categoryId, regionId, keyWord, pageable);

        ResponsePageDto response=ResponsePageDto.of(ResponseCode.SUCC_STORE_LIST_GET, data);
        return ResponseEntity.ok(response);
    }

    /**
     * 음식점 사장님 조회
     * @param principal  사용자 정보를 담고 있는 객체
     */
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/ownerList")
    public ResponseEntity<ResponseSingleDto> getStoreOwnerList(Principal principal){
        List<StoreListResponseDto> data=storeService.getOwnerStoreList(principal);

        ResponseSingleDto response=ResponseSingleDto.of(ResponseCode.SUCC_STORE_OWNER_LIST_GET, data);
        return ResponseEntity.ok(response);
    }

    /**
     * 음식점 상세조회
     * @param storeId   음식점 식별자
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<ResponseSingleDto> getStore(@PathVariable UUID storeId) {
        StoreGetResponseDto data = storeService.getStore(storeId);

        ResponseSingleDto<StoreGetResponseDto> response = ResponseSingleDto.of(ResponseCode.SUCC_STORE_GET, data);
        return ResponseEntity.ok(response);
    }

    /**
     * 음식점 수정
     * @param storeId   음식점 식별자
     * @param dto       음식점 정보 dto
     * 리소스 접근 사용자와 음식점 생성자가 동일한지 검사
     */
    @PreAuthorize("hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)")
    @PutMapping("/{storeId}")
    public ResponseEntity<ResponseDto> ModifyStore(@PathVariable UUID storeId, @RequestBody StoreModifyRequestDto dto) {
        storeService.modifyStore(storeId, dto);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_STORE_MODIFY);
        return ResponseEntity.ok(response);
    }

    /**
     * 음식점 삭제
     * @param storeId     음식점 식별자
     * @param principal   사용자 정보를 담고 있는 객체
     * 리소스 접근 사용자와 음식점 생성자가 동일한지 검사
     */
    @PreAuthorize("hasRole('OWNER') and @securityUtil.isStoreOwner(authentication,#storeId)")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ResponseDto> deleteStore(@PathVariable UUID storeId, Principal principal) {
        storeService.deleteStore(storeId, principal);

        ResponseDto response = ResponseDto.of(ResponseCode.SUCC_STORE_DELETE);
        return ResponseEntity.ok(response);
    }
}