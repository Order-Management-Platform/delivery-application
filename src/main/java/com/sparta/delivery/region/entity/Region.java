package com.sparta.delivery.region.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.region.dto.RegionRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "p_region")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class Region extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "region_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "region_name", unique = true)
    private String name;

    public static Region create(String name) {
        return Region.builder()
                .name(name)
                .build();
    }

    public void update(RegionRequestDto requestDto) {
        this.name = requestDto.getName();
    }
}
