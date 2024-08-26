package com.sparta.delivery.category.service;

import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import com.sparta.delivery.user.dto.SignUpRequest;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import com.sparta.delivery.user.jwt.UserDetailsServiceImpl;
import com.sparta.delivery.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // UserDetailsService 사용

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {

        SignUpRequest signUpRequest = new SignUpRequest("username",
                "nickName",
                "1234",
                UserRole.CUSTOMER,
                "test@test.com",
                "0100000000",
                "서울특별시 강남구",
                "31110");
        userService.createUser(signUpRequest);
        // 여기서 UserDetails를 모킹하거나 실제로 로드
        userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("test@test.com"); // 테스트용 사용자 이름 사용
        // SecurityContext에 사용자 설정
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );
    }
    @Transactional
    @Rollback(value = false)
    @Test
    void softDeleteTest() throws InterruptedException {
        Category category = new Category("한식");
        Category save = categoryRepository.save(category);
        UUID userId = userDetails.getUserId();

        Thread.sleep(200);
        save.delete(userId);
        Assertions.assertThat(save.getDeletedBy()).isEqualTo(userId);

    }


}