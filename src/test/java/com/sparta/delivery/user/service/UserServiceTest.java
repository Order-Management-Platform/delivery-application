package com.sparta.delivery.user.service;

import com.sparta.delivery.common.exception.BusinessException;
import com.sparta.delivery.user.dto.SignUpRequest;
import com.sparta.delivery.user.dto.UpdateUserRequest;
import com.sparta.delivery.user.dto.UserDetailInfoResponse;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserServiceTest {

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
    @DisplayName("회원 생성")
    void createUser() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username",
                "nickName",
                "1234",
                UserRole.CUSTOMER,
                "1234@test.com",
                "0100000000",
                "서울특별시 강남구",
                "31110");
        //when
        userService.createUser(signUpRequest);

        //then
        User user = userRepository.findByEmail(signUpRequest.getEmail()).orElse(null);
        assertEquals(user.getEmail(), signUpRequest.getEmail());
        assertTrue(passwordEncoder.matches("1234",user.getPassword()));

    }

    @Test
    @DisplayName("회원 목록 조회")
    void getUsers() {
        Pageable p = PageRequest.of(0, 10, Sort.by("createdBy").descending());
        Page<UserInfoResponse> users = userService.getUsers(p);

        assertThat(users.getContent().size()).isEqualTo(1);
        assertThat(users.getContent().get(0).getUserId()).isEqualTo(user.getId());

    }

    @Test
    @DisplayName("회원 조회")
    void getUserInfo() {
        UserDetailInfoResponse userInfo = userService.getUserInfo(user.getId());

        assertNotNull(userInfo);
        assertEquals(userInfo.getUserId(),user.getId());

    }

    @Test
    @DisplayName("회원 정보 수정")
    void updateUser() {
        //given
        UpdateUserRequest updateUserRequest = new UpdateUserRequest("test1", "test1", "4321", "01011111111", "천안시 서북구", "01113", UserRole.CUSTOMER);
        UUID userId = user.getId();

        //when
        userService.updateUser(userId, updateUserRequest);

        //then
        UserDetailInfoResponse userInfo = userService.getUserInfo(user.getId());
        assertThat(userInfo.getRole()).isEqualTo(updateUserRequest.getRole());
        assertThat(userInfo.getPhone()).isEqualTo(updateUserRequest.getPhone());
    }

    @Test
    @DisplayName("회원 정보 삭제")
    void deleteUser() {
        //given
        UUID userId = user.getId();

        //when
        userService.deleteUser(userId);
        em.flush();
        em.clear();
        //then

        assertThrows(BusinessException.class, ()->{
            UserDetailInfoResponse userInfo = userService.getUserInfo(userId);
        });

    }

    @Test
    @DisplayName("email 중복 검증")
    void checkEmail() {
        //given
        String email = user.getEmail();
        //when
        boolean result = userService.checkEmail(email);
        //then
        assertTrue(result);
    }
}