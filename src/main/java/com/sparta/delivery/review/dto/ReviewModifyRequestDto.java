package com.sparta.delivery.review.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewModifyRequestDto {
    private String content;
    private int rating;
}
