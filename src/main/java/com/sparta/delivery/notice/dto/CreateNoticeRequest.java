package com.sparta.delivery.notice.dto;

import com.sparta.delivery.notice.entity.NoticeAccess;
import lombok.Getter;

@Getter
public class CreateNoticeRequest {

    private String title;
    private String content;
    private NoticeAccess access;


}
