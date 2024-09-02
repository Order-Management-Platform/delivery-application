package com.sparta.delivery.common.dto;

import com.sparta.delivery.common.ResponseCode;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePageDto<T> {
    private int status;
    private String message;
    private List<T> content;
    private CustomPage pageInfo;

    // 삭제 예정
    public static <T> ResponsePageDto<T> of(final int status, final String message, final Page<T> page) {
        return ResponsePageDto.<T>builder()  // 제네릭 타입 명시
                .status(status)
                .message(message)
                .content(page.getContent())
                .pageInfo(CustomPage.fromPage(
                        page.getPageable(),
                        page.getTotalPages(),
                        page.getTotalElements()
                ))
                .build();
    }

    public static <T> ResponsePageDto<T> of(ResponseCode resCode,final Page<T> page) {
        return ResponsePageDto.<T>builder()  // 제네릭 타입 명시
                .status(resCode.getStatus())
                .message(resCode.getMessage())
                .content(page.getContent())
                .pageInfo(CustomPage.fromPage(
                        page.getPageable(),
                        page.getTotalPages(),
                        page.getTotalElements()
                ))
                .build();
    }

    @Getter
    @Builder
    public static class CustomPage {
        private int page;
        private int size;
        private long totalPage;
        private long totalCount;

        public static CustomPage fromPage(Pageable pageable, long totalPage, long totalElements) {
            return CustomPage.builder()
                    .page(pageable.getPageNumber())
                    .size(pageable.getPageSize())
                    .totalPage(totalPage)
                    .totalCount(totalElements)
                    .build();
        }
    }
}