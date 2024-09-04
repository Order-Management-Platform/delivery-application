package com.sparta.delivery.notice.controller;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.notice.dto.CreateNoticeRequest;
import com.sparta.delivery.notice.dto.NoticeListResponse;
import com.sparta.delivery.notice.dto.NoticeResponse;
import com.sparta.delivery.notice.dto.UpdateNoticeRequest;
import com.sparta.delivery.notice.service.NoticeService;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<ResponseDto> createNotice(@RequestBody CreateNoticeRequest createNoticeRequest) {
        noticeService.createNotice(createNoticeRequest);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_NOTICE_CREATE));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<ResponsePageDto<NoticeListResponse>> getNotices(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(page = 0,
                    size = 10,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable) {
        UserRole role = userDetails.getRole();
        Page<NoticeListResponse> responses = noticeService.getNotices(role, pageable);
        return ResponseEntity.ok(ResponsePageDto.of(ResponseCode.SUCC_NOTICE_LIST_GET, responses));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{noticeId}")
    public ResponseEntity<ResponseSingleDto<NoticeResponse>> getNotice(@PathVariable("noticeId") UUID noticeId,
                                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserRole role = userDetails.getRole();
        NoticeResponse noticeResponse = noticeService.getNotice(noticeId, role);
        return ResponseEntity.ok(ResponseSingleDto.of(ResponseCode.SUCC_NOTICE_GET, noticeResponse));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{noticeId}")
    public ResponseEntity<ResponseDto> updateNotice(@PathVariable("noticeId") UUID noticeId,
                                                    @RequestBody UpdateNoticeRequest updateNoticeRequest)
    {
        noticeService.updateNotice(noticeId, updateNoticeRequest);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_NOTICE_MODIFY));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ResponseDto> deleteNotice(@PathVariable("noticeId") UUID noticeId)
    {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_NOTICE_DELETE));
    }
}
