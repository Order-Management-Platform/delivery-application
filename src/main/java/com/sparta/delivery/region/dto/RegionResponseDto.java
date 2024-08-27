package com.sparta.delivery.region.dto;

import com.sparta.delivery.region.entity.Region;
import lombok.*;

import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RegionResponseDto {
    private UUID id;
    private String name;

    public static RegionResponseDto of(final Region region) {
        return RegionResponseDto.builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}
