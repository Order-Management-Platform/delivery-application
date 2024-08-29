package com.sparta.delivery.user.service;

import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public boolean isRoleMatch(String email, String role) {
        return userRepository.existsByEmailAndRole(email, UserRole.valueOf(role));
    }
}
