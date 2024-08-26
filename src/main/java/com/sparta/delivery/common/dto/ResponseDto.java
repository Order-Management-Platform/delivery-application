package com.sparta.delivery.common.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ResponseDto {

    private int status;
    private String message;

}
