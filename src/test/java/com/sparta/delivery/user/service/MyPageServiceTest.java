package com.sparta.delivery.user.service;

import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.user.dto.SignUpRequest;
import com.sparta.delivery.user.dto.UpdateMyPageRequest;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MyPageServiceTest {

    @Autowired
    MyPageService myPageService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;

    User user;

    @BeforeEach
    void setUp() {
        SignUpRequest signUpRequest = new SignUpRequest("username",
                "nickName",
                "1234",
                UserRole.CUSTOMER,
                "email@test.com",
                "0100000000",
                "서울특별시 강남구",
                "31110");
        //when
        userService.createUser(signUpRequest);
        user = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);
        assertNotNull(user);

    }


    @Test
    @DisplayName("마이페이지 조회")
    void myPageInfoTest() {
        //given
        String email = user.getEmail();

        //when
        UserInfoResponse userInfoResponse = myPageService.myPageInfo(email);

        //then
        assertThat(userInfoResponse).isNotNull();
        assertThat(userInfoResponse.getEmail()).isEqualTo(email);
        assertThat(userInfoResponse.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("마이페이지 정보수정")
    void updateMyPageTest() {
        //given
        String password = "0987654321";
        UpdateMyPageRequest updateMyPageRequest = new UpdateMyPageRequest("newNickName",
                 password,
                "01011111111",
                "천안시 서북구",
                "31111");
        UUID userId = user.getId();

        //when
        myPageService.updateMyPage(userId, updateMyPageRequest);
        em.flush();
        em.clear();

        User updateUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found"));
        //
        assertThat(passwordEncoder.matches(password, updateUser.getPassword())).isTrue();
        assertThat(updateUser.getPhone()).isEqualTo(updateMyPageRequest.getPhone());
    }

    @Test
    @DisplayName("마이페이지 회원탈퇴")
    void deleteMyPageTest() {
        //given
        UUID userId = user.getId();
        String email = user.getEmail();
        //when
        myPageService.deleteMyPage(userId);
        em.flush();
        em.clear();
        //then

        assertThatThrownBy(()->{
            myPageService.myPageInfo(email);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("회원 정보를 찾을 수 없습니다.");
    }


}