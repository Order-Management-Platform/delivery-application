package com.sparta.delivery.common.dto;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.store.dto.StoreGetResponseDto;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class ResponseSingleDto<T> {
    private int status;
    private String message;
    private T content;

    //todo : enum파라미터 생성자로 대체
    public static <T> ResponseSingleDto<T> of(final int status, final String message, final T content) {
        return ResponseSingleDto.<T>builder()
                .status(status)
                .message(message)
                .content(content)
                .build();
    }

    public static <T> ResponseSingleDto<T> of(ResponseCode resCode, final T content) {
        return ResponseSingleDto.<T>builder()
                .status(resCode.getStatus())
                .message(resCode.getMessage())
                .content(content)
                .build();
    }


}
