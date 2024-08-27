package com.sparta.delivery.user.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.user.dto.SignUpRequest;
import com.sparta.delivery.user.dto.UpdateUserRequest;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import com.sparta.delivery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email) {
        boolean result = userService.checkEmail(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signUp")
    public ResponseEntity<ResponseDto> createUser(@RequestBody SignUpRequest signUpRequest) {
        userService.createUser(signUpRequest);
        return ResponseEntity.ok(ResponseDto.of(200,"signUp successful"));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<ResponsePageDto<UserInfoResponse>> getUsers(
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<UserInfoResponse> users = userService.getUsers(pageable);
        return ResponseEntity.ok(ResponsePageDto.of(200, "조회 성공",users));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoResponse> getUserInfo(@PathVariable("userId") UUID userId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserInfoResponse userResponse = userService.getUserInfo(userId);
        return ResponseEntity.ok(userResponse);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{userId}")
    public ResponseEntity<ResponseDto> updateUser(@PathVariable("userId") UUID userId,
                                                  @RequestBody UpdateUserRequest updateUserRequest) {
        userService.updateUser(userId, updateUserRequest);
        return ResponseEntity.ok(ResponseDto.of(200, "user update successful"));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") UUID userId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ResponseDto.of(200, "user deleted successful."));
    }
}
