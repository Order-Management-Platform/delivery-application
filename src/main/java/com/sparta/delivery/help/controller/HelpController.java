package com.sparta.delivery.help.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.help.dto.HelpRequestDto;
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

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ResponseDto> createHelp(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody HelpRequestDto request
    ) {
        return ResponseEntity.ok(helpService.createHelp(userDetails.getUserId(), request));
    }

    @PutMapping("/{helpId}")
    @PreAuthorize("hasRole('CUSTOMER') and @securityUtil.checkHelpUser(#helpId, authentication)")
    public ResponseEntity<ResponseDto> updateHelp(
            @PathVariable(name = "helpId") UUID helpId,
            @RequestBody HelpRequestDto request
    ) {
        return ResponseEntity.ok(helpService.updateHelp(helpId, request));
    }
}
