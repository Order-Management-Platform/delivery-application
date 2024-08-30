package com.sparta.delivery.help.repository;

import com.sparta.delivery.help.entity.Help;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HelpRepository extends JpaRepository<Help, UUID> {
    Page<Help> findAllByUserId(UUID id, Pageable pageable);
}
