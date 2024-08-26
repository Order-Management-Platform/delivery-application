package com.sparta.delivery.region.entity;

import com.sparta.delivery.common.BaseEntity;
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
    @Column(name = "region_id", nullable = false)
    private UUID id;

    @Column(name = "region_name", unique = true)
    private String name;

    public static Region create(String name) {
        return Region.builder()
                .name(name)
                .build();
    }
}
