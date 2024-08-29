package com.sparta.delivery.notice.dto;

import com.sparta.delivery.notice.entity.Notice;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeResponse {

    private UUID noticeId;
    private String noticeTitle;
    private String noticeContent;
    private String author;
    private LocalDateTime createdAt;

    public static NoticeResponse of(Notice notice) {
        return NoticeResponse.builder()
                .noticeId(notice.getId())
                .noticeTitle(notice.getNoticeTitle())
                .noticeContent(notice.getNoticeContent())
                .author("관리자")
                .createdAt(notice.getUpdatedAt())
                .build();
    }

}
