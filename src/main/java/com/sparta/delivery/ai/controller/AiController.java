package com.sparta.delivery.ai.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.delivery.ai.entity.Ai;
import com.sparta.delivery.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;
    @PostMapping
    public ResponseEntity getAiDescription(@RequestParam String name) throws JsonProcessingException {
        String response = aiService.getAiDescription(name);
        return ResponseEntity.ok(response);
    }
}
