package com.sparta.delivery.ai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.delivery.ai.entity.Ai;
import com.sparta.delivery.ai.service.AiService;
import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    /**
     * 음식점 상품 설명 생성
     * @param name  상품 이름
     */
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    public ResponseEntity getAiDescription(@RequestParam String name) throws JsonProcessingException {
        String data = aiService.getAiDescription(name);
        ResponseSingleDto response= ResponseSingleDto.of(ResponseCode.SUCC_AI_GET, data);
        return ResponseEntity.ok(response);
    }
}
