package com.sparta.delivery.user.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.user.dto.UpdateMyPageRequest;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import com.sparta.delivery.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/myPage")
public class MyPageController {

    private final MyPageService myPageService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<UserInfoResponse> myPagInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        UserInfoResponse userIngoResponse = myPageService.myPageInfo(email);
        return ResponseEntity.ok(userIngoResponse);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping
    public ResponseEntity<ResponseDto> UpdateMyPage(@RequestBody UpdateMyPageRequest updateMyPageRequest,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        UUID userId = userDetails.getUserId();
        myPageService.updateMyPage(userId, updateMyPageRequest);
        return ResponseEntity.ok(new ResponseDto(200, "user update successful"));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUserId();
        myPageService.deleteMyPage(userId);
        return ResponseEntity.ok(new ResponseDto(200, "user delete successful"));
    }
}
