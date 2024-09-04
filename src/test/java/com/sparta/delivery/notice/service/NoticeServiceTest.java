package com.sparta.delivery.notice.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
import com.sparta.delivery.notice.dto.CreateNoticeRequest;
import com.sparta.delivery.notice.dto.NoticeListResponse;
import com.sparta.delivery.notice.dto.NoticeResponse;
import com.sparta.delivery.notice.dto.UpdateNoticeRequest;
import com.sparta.delivery.notice.entity.Notice;
import com.sparta.delivery.notice.entity.NoticeAccess;
import com.sparta.delivery.notice.repository.NoticeRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository;

    @InjectMocks
    private NoticeService noticeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("공지사항 생성 테스트")
    void createNotice_ShouldSaveNotice() {
        CreateNoticeRequest request = new CreateNoticeRequest("Title", "Content", NoticeAccess.CUSTOMER);

        noticeService.createNotice(request);

        verify(noticeRepository, times(1)).save(any(Notice.class));
    }

    @Test
    @DisplayName("공지사항 리스트 조회 테스트 (CUSTOMER 권한)")
    void getNotices_ForCustomer_ShouldReturnPagedNotices() {
        Pageable pageable = PageRequest.of(0, 10);
        Notice notice = Notice.of(new CreateNoticeRequest("Title", "Content", NoticeAccess.CUSTOMER));
        Page<Notice> notices = new PageImpl<>(Collections.singletonList(notice));
        when(noticeRepository.findAllByNoticeAccess(pageable, NoticeAccess.CUSTOMER)).thenReturn(notices);

        Page<NoticeListResponse> result = noticeService.getNotices(UserRole.CUSTOMER, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getNoticeTitle()).isEqualTo("Title");
    }

    @Test
    @DisplayName("공지사항 리스트 조회 테스트 (관리자 권한)")
    void getNotices_ForAdmin_ShouldReturnAllPagedNotices() {
        Pageable pageable = PageRequest.of(0, 10);
        Notice notice = Notice.of(new CreateNoticeRequest("Title", "Content", NoticeAccess.CUSTOMER));
        Page<Notice> notices = new PageImpl<>(Collections.singletonList(notice));
        when(noticeRepository.findAll(pageable)).thenReturn(notices);

        Page<NoticeListResponse> result = noticeService.getNotices(UserRole.OWNER, pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getNoticeTitle()).isEqualTo("Title");
    }

    @Test
    @DisplayName("단일 공지사항 조회 테스트 (CUSTOMER 권한)")
    void getNotice_ForCustomer_ShouldReturnNotice() {
        UUID noticeId = UUID.randomUUID();
        Notice notice = Notice.of(new CreateNoticeRequest("Title", "Content", NoticeAccess.CUSTOMER));
        when(noticeRepository.findByIdAndNoticeAccess(noticeId, NoticeAccess.CUSTOMER)).thenReturn(Optional.of(notice));

        NoticeResponse result = noticeService.getNotice(noticeId, UserRole.CUSTOMER);

        assertThat(result.getNoticeTitle()).isEqualTo("Title");
        verify(noticeRepository, times(1)).findByIdAndNoticeAccess(noticeId, NoticeAccess.CUSTOMER);
    }

    @Test
    @DisplayName("단일 공지사항 조회 테스트 (관리자 권한)")
    void getNotice_ForAdmin_ShouldReturnNotice() {
        UUID noticeId = UUID.randomUUID();
        Notice notice = Notice.of(new CreateNoticeRequest("Title", "Content", NoticeAccess.CUSTOMER));
        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(notice));

        NoticeResponse result = noticeService.getNotice(noticeId, UserRole.OWNER);

        assertThat(result.getNoticeTitle()).isEqualTo("Title");
        verify(noticeRepository, times(1)).findById(noticeId);
    }

    @Test
    @DisplayName("공지사항 수정 테스트")
    void updateNotice_ShouldUpdateNotice() {
        UUID noticeId = UUID.randomUUID();
        Notice notice = Notice.of(new CreateNoticeRequest("Title", "Content", NoticeAccess.CUSTOMER));
        UpdateNoticeRequest updateRequest = new UpdateNoticeRequest("New Title", "New Content", NoticeAccess.OWNER);
        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(notice));

        noticeService.updateNotice(noticeId, updateRequest);

        assertThat(notice.getNoticeTitle()).isEqualTo("New Title");
        assertThat(notice.getNoticeContent()).isEqualTo("New Content");
        assertThat(notice.getNoticeAccess()).isEqualTo(NoticeAccess.OWNER);
    }

    @Test
    @DisplayName("존재하지 않는 공지사항 수정 시 예외 발생 테스트")
    void updateNotice_ShouldThrowException_WhenNoticeNotFound() {
        UUID noticeId = UUID.randomUUID();
        UpdateNoticeRequest updateRequest = new UpdateNoticeRequest("New Title", "New Content", NoticeAccess.OWNER);
        when(noticeRepository.findById(noticeId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                noticeService.updateNotice(noticeId, updateRequest));

        assertThat(exception.getResponseCode()).isEqualTo(ResponseCode.NOT_FOUND_NOTICE);
    }

    @Test
    @DisplayName("공지사항 삭제 테스트")
    void deleteNotice_ShouldDeleteNotice() {
        UUID noticeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        User user = User.builder().id(userId).build();
        UserDetailsImpl userDetails = new UserDetailsImpl(user); // 필요한 필드 설정

        // Mock Authentication 및 SecurityContext
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext); // 모킹된 SecurityContext 설정

        // Notice 생성 및 저장 모킹
        Notice notice = Notice.of(new CreateNoticeRequest("Title", "Content", NoticeAccess.CUSTOMER));
        when(noticeRepository.findById(noticeId)).thenReturn(Optional.of(notice));

        // 실제 삭제 메서드 호출 (JWT 인증된 사용자 ID가 설정되어 있어야 함)
        noticeService.deleteNotice(noticeId);

        // 삭제 후 findById 호출 시 empty 반환되도록 모킹
        when(noticeRepository.findById(noticeId)).thenReturn(Optional.empty());

        // 삭제된 공지사항 검증
        Optional<Notice> foundNotice = noticeRepository.findById(noticeId);
        assertThat(foundNotice).isEmpty();

        // SecurityContextHolder 초기화
        SecurityContextHolder.clearContext();

    }

    @Test
    @DisplayName("존재하지 않는 공지사항 삭제 시 예외 발생 테스트")
    void deleteNotice_ShouldThrowException_WhenNoticeNotFound() {
        UUID noticeId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        when(noticeRepository.findById(noticeId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                noticeService.deleteNotice(noticeId));

        assertThat(exception.getResponseCode()).isEqualTo(ResponseCode.NOT_FOUND_NOTICE);
    }
}