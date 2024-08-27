package com.sparta.delivery.store.controller;

import com.sparta.delivery.store.dto.StoreGetResponseDto;
import com.sparta.delivery.store.dto.StoreModifyRequestDto;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

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


    @GetMapping
    public void getStoreList(@RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, defaultValue = "10") int size,
                             @RequestParam(required = false, defaultValue = "false") boolean asc,
                             @RequestParam(required = false, defaultValue = "name") String sort,
                             @RequestParam(required = false) UUID categoryId,
                             @RequestParam(required = false) String keyWord
    ) {
        Pageable pageable = asc ? PageRequest.of(page, size, Sort.by(sort).ascending()) :
                PageRequest.of(page, size, Sort.by(sort).descending());
        // storeService.getStoreList();
    }

    @GetMapping("/{storeId}")
    public StoreGetResponseDto getStore(@PathVariable UUID storeId) {
        return storeService.getStore(storeId);
    }

    @PutMapping("/{storeId}")
    public void ModifyStore(@PathVariable UUID storeId, @RequestBody StoreModifyRequestDto dto) {
        storeService.modifyStore(storeId, dto);
    }

    @DeleteMapping("/{storeId}")
    public void deleteStore(@PathVariable UUID storeId) {
        storeService.deleteStore(storeId);
    }
}