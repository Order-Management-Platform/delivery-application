package com.sparta.delivery.user.service;

import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.user.dto.UpdateMyPageRequest;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse myPageInfo(String email) {
        return userRepository.findByEmail(email)
                .map(UserInfoResponse::of)
                .orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
    }


    @Transactional
    public void updateMyPage(UUID userId, UpdateMyPageRequest updateMyPageRequest) {
        String encodedPassword = passwordEncoder.encode(updateMyPageRequest.getPassword());
        updateMyPageRequest.encodingPassword(encodedPassword);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));

        user.updateMyPage(updateMyPageRequest);
    }

    @Transactional
    public void deleteMyPage(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));

        user.delete(userId);
    }
}
