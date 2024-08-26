package com.sparta.delivery.region.controller;

import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.region.dto.ResponseDto;
import com.sparta.delivery.region.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(final RegionService regionService) {
        this.regionService = regionService;
    }

    // 지역 생성
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> createRegion(@RequestParam(name = "name") String regionName) {
        ResponseDto<Void> response = regionService.createRegion(regionName);
        return ResponseEntity.ok(response);
    }

    // 지역 조회
    @GetMapping
    public ResponseEntity<ResponseDto<RegionResponseDto>> getRegion(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("asc") boolean asc
    ) {
        return ResponseEntity.ok(regionService.getRegion(page, size, sort, asc));
    }
}
