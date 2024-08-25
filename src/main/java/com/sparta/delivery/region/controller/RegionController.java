package com.sparta.delivery.region.controller;

import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.region.service.RegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(final RegionService regionService) {
        this.regionService = regionService;
    }

    //지역 생성
    @PostMapping
    public ResponseEntity<RegionResponseDto> createRegion(@RequestParam(name = "name") String regionName) {
        RegionResponseDto response = regionService.createRegion(regionName);
        return ResponseEntity.ok(response);
    }
}
