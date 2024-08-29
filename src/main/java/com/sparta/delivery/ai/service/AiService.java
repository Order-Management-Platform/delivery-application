package com.sparta.delivery.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery.ai.entity.Ai;
import com.sparta.delivery.ai.repository.AiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${api.secret.key}")
    private String key;

    private final AiRepository aiRepository;

    /**
     * Get AI description and save it
     */
    public String getAiDescription(String name) throws JsonProcessingException {

        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + key;
        String jsonString = "{\"contents\":[{\"parts\":[{\"text\":\"음식점 메뉴"+ name+"를 설명하는 글 한줄 작성해줘\"}]}]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        String body = responseEntity.getBody();
        if (body == null) {
            throw new RuntimeException("Empty response body");
        }

        String description = parsing(body);

        Ai ai = Ai.builder().name(name).description(description).build();
        aiRepository.save(ai);

        return description;
    }

    private String parsing(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(body);
        JsonNode candidates = root.path("candidates");
        if (candidates.isArray() && candidates.size() > 0) {
            return candidates.get(0).path("content").path("parts").get(0).path("text").asText();
        }
        throw new RuntimeException("Unexpected JSON structure");
    }
}
