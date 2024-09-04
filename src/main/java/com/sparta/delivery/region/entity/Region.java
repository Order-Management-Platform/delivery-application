package com.sparta.delivery.region.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.region.dto.RegionRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "p_region")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@SQLRestriction("deleted_at is null")
public class Region extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "region_id", nullable = false)
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
