package com.sparta.delivery.region.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.region.dto.RegionRequestDto;
import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.region.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(final RegionService regionService) {
        this.regionService = regionService;
    }

    // 지역 생성
    @PostMapping
    public ResponseEntity<ResponseDto> createRegion(@RequestParam(name = "name") String regionName) {
        ResponseDto response = regionService.createRegion(regionName);
        return ResponseEntity.ok(response);
    }

    // 지역 조회
    @GetMapping
    public ResponseEntity<ResponsePageDto<RegionResponseDto>> getRegion(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("asc") boolean asc
    ) {
        return ResponseEntity.ok(regionService.getRegion(page, size, sort, asc));
    }

    // 지역 수정
    @PutMapping
    public ResponseEntity<ResponseDto> updateRegion(@RequestBody RegionRequestDto request) {
        ResponseDto response = regionService.updateRegion(request);
        return ResponseEntity.ok(response);
    }

    // 지역 삭제
    @DeleteMapping("/{regionId}")
    public ResponseEntity<ResponseDto> deleteRegion(@PathVariable(name = "regionId") UUID regionId) {
        ResponseDto response = regionService.deleteRegion(regionId);
        return ResponseEntity.ok(response);
    }
}
