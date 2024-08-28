package com.sparta.delivery.ai.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery.ai.entity.Ai;
import com.sparta.delivery.ai.repository.AiRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AiService {

    @Value("${api.secret.key}")
    private String key;

    private final AiRepository aiRepository;

    /**
     * description 요청과 저장
     */
    public String getAiDescription(String name) throws JsonProcessingException {

        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyBp710KTVAi6VbmGzRnny3dYum2JzFijbs";
        String jsonString = "{\"contents\":[{\"parts\":[{\"text\":\"텍스트 언어 - korean, 맥락 -만두를 홍보하는 문구를 작성해줘. 예를 들어 갓 빚은 뜨끈한 만두로 속까지 따뜻하게 채워보세요!와 같은 문장으로 작성해줘\"}]}]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                apiUrl,
                jsonString,
                String.class
        );
        System.out.println(responseEntity.getBody());
        String body = responseEntity.getBody();
        String description = parsing(body);

        Ai ai = Ai.builder().name(name).description(description).build();
        aiRepository.save(ai);
        return description;
    }

    private String parsing(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(body)
                .path("candidates").get(0)
                .path("content").path("parts").get(0)
                .path("text").asText();
    }
}
