package com.sparta.delivery.user.controller;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ErrorsResponseDto;
import com.sparta.delivery.common.dto.FieldError;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.user.dto.UpdateMyPageRequest;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import com.sparta.delivery.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/myPage")
@PreAuthorize("hasRole('CUSTOMER')")
public class MyPageController {

    private final MyPageService myPageService;


    @GetMapping
    public ResponseEntity<UserInfoResponse> myPagInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String email = userDetails.getUsername();
        UserInfoResponse userIngoResponse = myPageService.myPageInfo(email);
        return ResponseEntity.ok(userIngoResponse);
    }

    @PutMapping
    public ResponseEntity<?> UpdateMyPage(@Validated @RequestBody UpdateMyPageRequest updateMyPageRequest,
                                                    BindingResult bindingResult,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ErrorsResponseDto.of(ResponseCode.BAD_REQUEST, FieldError.of(bindingResult)));
        }

        UUID userId = userDetails.getUserId();
        myPageService.updateMyPage(userId, updateMyPageRequest);
        return ResponseEntity.ok(ResponseDto.of(200,"user update successful"));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUserId();
        myPageService.deleteMyPage(userId);
        return ResponseEntity.ok(ResponseDto.of(200,"user delete successful"));
    }
}
