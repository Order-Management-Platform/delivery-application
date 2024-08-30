package com.sparta.delivery.review.dto;

import com.sparta.delivery.review.Review;
import lombok.*;

import java.util.UUID;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewListResponseDto {
    private UUID reviewId;
    private String nickName;
    private int rating;
    private String content;

    public static ReviewListResponseDto of(Review review){
        return ReviewListResponseDto.builder()
                .reviewId(review.getId())
                .nickName(review.getUser().getNickName())
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }
}
