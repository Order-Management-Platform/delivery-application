package com.sparta.delivery.notice.repository;

import com.sparta.delivery.notice.entity.Notice;
import com.sparta.delivery.notice.entity.NoticeAccess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface NoticeRepository extends JpaRepository<Notice, UUID> {
    Page<Notice> findAllByNoticeAccess(Pageable pageable, NoticeAccess noticeAccess);

    Optional<Notice> findByIdAndNoticeAccess(UUID noticeId, NoticeAccess noticeAccess);
}
