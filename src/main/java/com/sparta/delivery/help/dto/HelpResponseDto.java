package com.sparta.delivery.help.dto;

import com.sparta.delivery.help.entity.Help;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpResponseDto {
    private UUID helpId;
    private String title;
    private String question;
    private String answer;
    private LocalDateTime createdAt;

    public static HelpResponseDto of(Help help) {
        return HelpResponseDto.builder()
                .helpId(help.getId())
                .title(help.getTitle())
                .question(help.getQuestion())
                .answer(help.getAnswer())
                .createdAt(help.getCreatedAt())
                .build();
    }
}
