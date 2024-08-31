package com.sparta.delivery.user.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(cacheNames = "getRole", key = "#email")
    public UserRole getRole(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRole)
                .orElseThrow(()-> new BusinessException(ResponseCode.NOT_FOUND_USER));
    }
}
