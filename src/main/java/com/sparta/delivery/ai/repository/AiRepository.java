package com.sparta.delivery.ai.repository;

import com.sparta.delivery.ai.entity.Ai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRepository extends JpaRepository<Ai,Long> {
}
