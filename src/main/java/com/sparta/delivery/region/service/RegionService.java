package com.sparta.delivery.region.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.region.dto.RegionRequestDto;
import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.region.entity.Region;
import com.sparta.delivery.region.repository.RegionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(final RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    // 지역 생성 로직
    @Transactional
    public ResponseDto createRegion(String regionName) {
        regionRepository.save(Region.create(regionName));
        return ResponseDto.of(ResponseCode.SUCC_REGION_CREATE);
    }

    // 지역 조회 로직
    public ResponsePageDto<RegionResponseDto> getRegion(int page, int size, String sort, boolean asc, String keyword) {
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(direction, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<Region> regionList = keyword == null ?
                regionRepository.findAll(pageable) : regionRepository.findAllByNameContaining(keyword, pageable);
        Page<RegionResponseDto> regionResponseDtoPage = regionList.map(RegionResponseDto::of);
        return ResponsePageDto.of(ResponseCode.SUCC_REGION_GET, regionResponseDtoPage);
    }

    // 지역 수정 로직
    @Transactional
    public ResponseDto updateRegion(RegionRequestDto request) {
        Region region = regionRepository.findById(request.getRegionId()).orElseThrow(() ->
                new NotFoundException(ResponseCode.NOT_FOUND_REGiON));
        region.update(request);
        return ResponseDto.of(ResponseCode.SUCC_REGION_UPDATE);
    }

    // 지역 삭제 로직
    @Transactional
    public ResponseDto deleteRegion(UUID regionId, UUID userId) {
        Region region = regionRepository.findById(regionId).orElseThrow(() ->
                new NotFoundException(ResponseCode.NOT_FOUND_REGiON));
        region.delete(userId);
        return ResponseDto.of(ResponseCode.SUCC_REGION_DELETE);
    }
}
