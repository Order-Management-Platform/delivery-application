package com.sparta.delivery.review.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDto {

    private UUID storeId;
    private String content;
    private int rating;

}
