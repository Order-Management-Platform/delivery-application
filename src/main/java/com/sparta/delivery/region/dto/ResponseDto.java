package com.sparta.delivery.region.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private int status;
    private String message;
    private List<T> content;
    private CustomPageable pageable;

    // content가 없는 경우
    public static <T> ResponseDto<T> of(final int status, final String message) {
        return ResponseDto.<T>builder()  // 제네릭 타입 명시
                .status(status)
                .message(message)
                .build();
    }

    // content가 있는 경우
    public static <T> ResponseDto<T> of(final int status, final String message, final Page<T> page) {
        return ResponseDto.<T>builder()  // 제네릭 타입 명시
                .status(status)
                .message(message)
                .content(page.getContent())
                .pageable(CustomPageable.fromPage(
                        page.getPageable(),
                        page.getTotalPages(),
                        page.getTotalElements()
                ))
                .build();
    }

    @Getter
    @Builder
    public static class CustomPageable {
        private int page;
        private int size;
        private long totalPage;
        private long totalCount;

        public static CustomPageable fromPage(Pageable pageable, long totalPage, long totalElements) {
            return CustomPageable.builder()
                    .page(pageable.getPageNumber())
                    .size(pageable.getPageSize())
                    .totalPage(totalPage)
                    .totalCount(totalElements)
                    .build();
        }
    }
}

