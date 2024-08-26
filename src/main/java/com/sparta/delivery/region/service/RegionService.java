package com.sparta.delivery.region.service;

import com.sparta.delivery.region.dto.RegionRequestDto;
import com.sparta.delivery.region.dto.RegionResponseDto;
import com.sparta.delivery.region.dto.ResponseDto;
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
    public ResponseDto<Void> createRegion(String regionName) {
        regionRepository.save(Region.create(regionName));
        return ResponseDto.of(200, "지역 생성 성공");
    }

    // 지역 조회 로직
    public ResponseDto<RegionResponseDto> getRegion(int page, int size, String sort, boolean asc) {
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(direction, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<Region> regionList = regionRepository.findAll(pageable);
        Page<RegionResponseDto> regionResponseDtoPage = regionList.map(RegionResponseDto::of);
        return ResponseDto.of(200, "지역 조회 성공", regionResponseDtoPage);
    }

    // 지역 수정 로직
    @Transactional
    public ResponseDto<Void> updateRegion(RegionRequestDto request) {
        Region region = regionRepository.findById(request.getRegionId()).orElseThrow(() ->
                new NullPointerException("해당 지역을 찾을 수 없습니다."));
        region.update(request);
        return ResponseDto.of(200, "지역 수정 성공");
    }

    @Transactional
    public ResponseDto<Void> deleteRegion(UUID regionId) {
        Region region = regionRepository.findById(regionId).orElseThrow(() ->
                new NullPointerException("해당 지역을 찾을 수 없습니다."));
        region.delete(regionId); // 유저 아이디로 변경해야함 - kyeonkim
        return ResponseDto.of(200, "지역 삭제 성공");
    }
}
