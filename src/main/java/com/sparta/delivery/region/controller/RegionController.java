package com.sparta.delivery.region.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.region.dto.RegionRequestDto;
import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.region.service.RegionService;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ResponseDto> createRegion(@RequestParam(name = "name") String regionName) {
        ResponseDto response = regionService.createRegion(regionName);
        return ResponseEntity.ok(response);
    }

    // 지역 조회
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<ResponsePageDto<RegionResponseDto>> getRegion(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(value = "asc", required = false, defaultValue = "false") boolean asc,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        return ResponseEntity.ok(regionService.getRegion(page - 1, size, sort, asc, keyword));
    }

    // 지역 수정
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping
    public ResponseEntity<ResponseDto> updateRegion(@RequestBody RegionRequestDto request) {
        ResponseDto response = regionService.updateRegion(request);
        return ResponseEntity.ok(response);
    }

    // 지역 삭제
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{regionId}")
    public ResponseEntity<ResponseDto> deleteRegion(
            @PathVariable(name = "regionId") UUID regionId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        ResponseDto response = regionService.deleteRegion(regionId, userDetails.getUserId());
        return ResponseEntity.ok(response);
    }
}
