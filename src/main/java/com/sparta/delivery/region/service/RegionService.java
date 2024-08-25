package com.sparta.delivery.region.service;

import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.region.entity.Region;
import com.sparta.delivery.region.repository.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(final RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    // 지역 생성 로직
    @Transactional
    public RegionResponseDto createRegion(String regionName) {
        regionRepository.save(Region.create(regionName));
        return RegionResponseDto.of(200, "지역 생성 성공");
    }
}
