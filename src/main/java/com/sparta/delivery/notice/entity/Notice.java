package com.sparta.delivery.notice.entity;

import com.sparta.delivery.common.BaseEntity;
import com.sparta.delivery.notice.dto.CreateNoticeRequest;
import com.sparta.delivery.notice.dto.UpdateNoticeRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "p_notice")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@SQLRestriction("deleted_by is null")
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String noticeTitle;

    @Column(columnDefinition = "TEXT")
    private String noticeContent;

    @Enumerated(value = EnumType.STRING)
    private NoticeAccess noticeAccess;

    public static Notice of(CreateNoticeRequest noticeRequest) {
        return Notice.builder()
                .noticeTitle(noticeRequest.getTitle())
                .noticeContent(noticeRequest.getContent())
                .noticeAccess(noticeRequest.getAccess())
                .build();
    }


    public void update(UpdateNoticeRequest updateNoticeRequest) {
        noticeTitle = updateNoticeRequest.getTitle();
        noticeContent = updateNoticeRequest.getContent();
        noticeAccess = updateNoticeRequest.getAccess();
    }

    public void delete(UUID userId) {
        this.markDeleted(userId);
    }
}
