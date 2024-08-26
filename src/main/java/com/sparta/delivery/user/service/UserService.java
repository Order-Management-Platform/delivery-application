package com.sparta.delivery.user.service;

import com.sparta.delivery.user.dto.SignUpRequest;
import com.sparta.delivery.user.dto.UpdateUserRequest;
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


    @Transactional
    public void createUser(SignUpRequest signUpRequest) {
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        signUpRequest.encodingPassword(encodedPassword);
        User user = User.toEntity(signUpRequest);
        userRepository.save(user);
    }

    public Page<UserInfoResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserInfoResponse::of);
    }

    public UserInfoResponse getUserInfo(UUID userId) {
        return userRepository.findById(userId)
                .map(UserInfoResponse::of)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }



    @Transactional
    public void updateUser(UUID userId, UpdateUserRequest updateUserRequest) {
        String encodedPassword = passwordEncoder.encode(updateUserRequest.getPassword());
        updateUserRequest.encodingPassword(encodedPassword);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        user.updateUser(updateUserRequest);
    }


    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));

        user.delete(userId);
    }


}
