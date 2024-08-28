package com.sparta.delivery.store.controller;

import com.sparta.delivery.store.dto.StoreGetResponseDto;
import com.sparta.delivery.store.dto.StoreListResponseDto;
import com.sparta.delivery.store.dto.StoreModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final StoreRepository storeRepository;

    //관리자 페이지 - 가게 생성
    @PostMapping
    public void createStore(@RequestBody Map<String, String> map) {
        Store store = Store.builder()
                .name(map.get("name"))
                .build();
        storeRepository.save(store);
    }


    /**
     * 음식점 조회
     */
    //todo : request dto로 변경
    //todo : 조회 sql 리팩토링 필요 - keyword포함에 대해
    //todo : region 필수라면 pathval로 변경
    @GetMapping
    public Page<StoreListResponseDto> getStoreList(@RequestParam(required = false, defaultValue = "1") int page,
                                                   @RequestParam(required = false, defaultValue = "10") int size,
                                                   @RequestParam(required = false, defaultValue = "false") boolean asc,
                                                   @RequestParam(required = false, defaultValue = "name") String sort,
                                                   @RequestParam(required = false) UUID categoryId,
                                                   @RequestParam(required = false, defaultValue="") String keyWord,
                                                   @RequestParam(required=true) UUID regionId
    ) {
        Pageable pageable = asc ? PageRequest.of(page-1, size, Sort.by(sort).ascending()) :
                PageRequest.of(page, size, Sort.by(sort).descending());
        return storeService.getStoreList(categoryId, regionId, keyWord,pageable);
    }

    /**
     * 음식점 사장님 조회
     */
    //owener - 지역 파라미터 필수 , owner도 getStoreList()메서드에 접근 가능
    //@PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @GetMapping("/ownerList")
    public List<Store> getStoreOwnerList(Principal principal){
        return storeService.getOwnerStoreList(principal);

    }

    /**
     *  음식점 상세조회
     */
    //@PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @GetMapping("/{storeId}")
    public StoreGetResponseDto getStore(@PathVariable UUID storeId) {
        return storeService.getStore(storeId);
    }

    /**
     * 음식점 수정
     */
    //@PreAuthorize("isAuthenticated() and hasRole('OWNER')")
    @PutMapping("/{storeId}")
    public void ModifyStore(@PathVariable UUID storeId, @RequestBody StoreModifyRequestDto dto) {
        storeService.modifyStore(storeId, dto);
    }

    /**
     * 음식점 삭제
     */
    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
    }
}