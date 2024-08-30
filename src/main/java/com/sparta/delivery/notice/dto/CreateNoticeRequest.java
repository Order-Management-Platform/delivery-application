package com.sparta.delivery.notice.dto;

import com.sparta.delivery.notice.entity.NoticeAccess;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateNoticeRequest {

    private String title;
    private String content;
    private NoticeAccess access;


}
