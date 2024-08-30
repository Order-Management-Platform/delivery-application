package com.sparta.delivery.help.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.help.dto.HelpRequestDto;
import com.sparta.delivery.help.dto.HelpResponseDto;
import com.sparta.delivery.help.service.HelpService;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/help")
@RequiredArgsConstructor
public class HelpController {

    private final HelpService helpService;

    // 문의 생성
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDto> createHelp(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody HelpRequestDto request
    ) {
        return ResponseEntity.ok(helpService.createHelp(userDetails.getUserId(), request));
    }

    // 문의 전체 조회
    @GetMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ResponsePageDto<HelpResponseDto>> getHelp(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(value = "asc", required = false, defaultValue = "false") boolean asc
    ) {
        return ResponseEntity.ok(helpService.getHelp(page - 1, size, sort, asc));
    }

    // 문의 수정
    @PutMapping("/{helpId}")
    @PreAuthorize("hasRole('CUSTOMER') and @securityUtil.checkHelpUser(#helpId, authentication)")
    public ResponseEntity<ResponseDto> updateHelp(
            @PathVariable(name = "helpId") UUID helpId,
            @RequestBody HelpRequestDto request
    ) {
        return ResponseEntity.ok(helpService.updateHelp(helpId, request));
    }

    // 문의 삭제
    @DeleteMapping("/{helpId}")
    @PreAuthorize("hasRole('CUSTOMER') and @securityUtil.checkHelpUser(#helpId, authentication)")
    public ResponseEntity<ResponseDto> deleteHelp(
            @PathVariable(name = "helpId") UUID helpId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(helpService.deleteHelp(helpId, userDetails.getUserId()));

    }
}
