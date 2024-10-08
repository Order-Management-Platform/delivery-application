package com.sparta.delivery.user.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
import com.sparta.delivery.user.dto.SignUpRequest;
import com.sparta.delivery.user.dto.UpdateUserRequest;
import com.sparta.delivery.user.dto.UserDetailInfoResponse;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CacheService cacheService;


    @Transactional
    public void createUser(SignUpRequest signUpRequest) {
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.encodingPassword(encodedPassword);

        if (checkEmail(signUpRequest.getEmail())) {
            throw new BusinessException(ResponseCode.DUPLICATE_EMAIL);
        }

        User user = User.toEntity(signUpRequest);
        userRepository.save(user);
    }

    public Page<UserInfoResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserInfoResponse::of);
    }

    public UserDetailInfoResponse getUserInfo(UUID userId) {
        return userRepository.findById(userId)
                .map(UserDetailInfoResponse::of)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));
    }



    @Transactional
    public void updateUser(UUID userId, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));
        String encodedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
        updateUserRequest.encodingPassword(encodedPassword);
        String oldRole = user.getRole().name();

        if (!oldRole.equals(updateUserRequest.getRole().name())) {
            cacheService.evictRole(user.getEmail());
        }

        user.updateUser(updateUserRequest);
    }


    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));

        user.delete(userId);
    }


    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
