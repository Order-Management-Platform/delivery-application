package com.sparta.delivery.region.repository;

import com.sparta.delivery.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
