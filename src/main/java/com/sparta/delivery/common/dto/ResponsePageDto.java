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
    private CustomPageable pageable;

    public static <T> ResponsePageDto<T> of(final int status, final String message, final Page<T> page) {
        return ResponsePageDto.<T>builder()  // 제네릭 타입 명시
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
    public static <T> ResponsePageDto<T> of(ResponseCode resCode,final Page<T> page) {
        return ResponsePageDto.<T>builder()  // 제네릭 타입 명시
                .status(resCode.getStatus())
                .message(resCode.getMessage())
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