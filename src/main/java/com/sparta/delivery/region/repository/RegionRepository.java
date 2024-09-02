package com.sparta.delivery.region.repository;

import com.sparta.delivery.region.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {
    Page<Region> findAllByNameContaining(String keyword, Pageable pageable);
}
