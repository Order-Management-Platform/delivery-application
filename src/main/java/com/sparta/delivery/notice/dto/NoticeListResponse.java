package com.sparta.delivery.notice.dto;

import com.sparta.delivery.notice.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NoticeListResponse {

    private UUID noticeId;
    private String noticeTitle;
    private String author;
    private LocalDateTime createdAt;

    public static NoticeListResponse of(Notice notice) {
        return NoticeListResponse.builder()
                .noticeId(notice.getId())
                .noticeTitle(notice.getNoticeTitle())
                .author("관리자")
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
