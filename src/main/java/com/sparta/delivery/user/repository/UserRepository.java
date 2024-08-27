package com.sparta.delivery.user.repository;

import com.sparta.delivery.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>  {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
