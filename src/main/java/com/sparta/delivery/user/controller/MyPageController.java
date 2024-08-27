package com.sparta.delivery.user.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.user.dto.UpdateMyPageRequest;
import com.sparta.delivery.user.dto.UserInfoResponse;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import com.sparta.delivery.user.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseDto> UpdateMyPage(@Validated @RequestBody UpdateMyPageRequest updateMyPageRequest,
                                                    BindingResult bindingResult,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        if (bindingResult.hasErrors()) {
            // 에러 메세지 로직 변경 필요 할듯.
            String errorMessage = bindingResult.getAllErrors().stream().findFirst().get().getDefaultMessage();
            return ResponseEntity
                    .badRequest()
                    .body(ResponseDto.of(HttpStatus.BAD_REQUEST.value(),errorMessage));
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
