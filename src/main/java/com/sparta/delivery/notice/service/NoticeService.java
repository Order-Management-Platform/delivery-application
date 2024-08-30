package com.sparta.delivery.notice.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.notice.dto.CreateNoticeRequest;
import com.sparta.delivery.notice.dto.NoticeListResponse;
import com.sparta.delivery.notice.dto.NoticeResponse;
import com.sparta.delivery.notice.dto.UpdateNoticeRequest;
import com.sparta.delivery.notice.entity.Notice;
import com.sparta.delivery.notice.entity.NoticeAccess;
import com.sparta.delivery.notice.repository.NoticeRepository;
import com.sparta.delivery.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;


    @Transactional
    public void createNotice(CreateNoticeRequest createNoticeRequest) {
        Notice notice = Notice.of(createNoticeRequest);
        noticeRepository.save(notice);
    }


    public Page<NoticeListResponse> getNotices(UserRole role, Pageable pageable) {
        if (role == UserRole.CUSTOMER) {
            return noticeRepository.findAllByNoticeAccess(pageable, NoticeAccess.CUSTOMER).map(NoticeListResponse::of);
        }
        return noticeRepository.findAll(pageable).map(NoticeListResponse::of);
    }

    public NoticeResponse getNotice(UUID noticeId, UserRole role) {
        if (role == UserRole.CUSTOMER) {
            return noticeRepository.findByIdAndNoticeAccess(noticeId, NoticeAccess.CUSTOMER).map(NoticeResponse::of)
                    .orElseThrow(()-> new NotFoundException(ResponseCode.NOT_FOUND_NOTICE));
        }

        return noticeRepository.findById(noticeId).map(NoticeResponse::of)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_NOTICE));
    }


    @Transactional
    public void updateNotice(UUID noticeId, UpdateNoticeRequest updateNoticeRequest) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_NOTICE));

        notice.update(updateNoticeRequest);
    }

    @Transactional
    public void deleteNotice(UUID noticeId, UUID userId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_NOTICE));

        notice.delete(userId);
    }



}
