package com.sparta.delivery.user;

import com.sparta.delivery.user.dto.SignUpRequest;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import com.sparta.delivery.user.service.UserService;
import jakarta.persistence.PrePersist;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Rollback(value = false)
public class UserTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createTest() {
        SignUpRequest signUpRequest = new SignUpRequest("username",
                "nickName",
                "1234",
                UserRole.CUSTOMER,
                "email@test.com",
                "0100000000",
                "서울특별시 강남구",
                "31110");
        userService.createUser(signUpRequest);

        User user = userRepository.findByEmail("email@test.com").get();
        Assertions.assertThat(user).isNotNull();


    }

    @Test
    void findByIdTest() {

    }
}
