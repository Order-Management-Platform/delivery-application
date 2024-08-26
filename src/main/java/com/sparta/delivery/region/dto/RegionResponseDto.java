package com.sparta.delivery.region.dto;

import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RegionResponseDto {
    private int status;
    private String message;

    public static RegionResponseDto of(final int status, final String message) {
        return RegionResponseDto.builder()
                .status(status)
                .message(message)
                .build();
    }
}
