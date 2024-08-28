package com.sparta.delivery.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    //상품 성공
    SUCC_PRODUCT_MODIFY(HttpStatus.OK.value(), "상품 수정 성공"),
    SUCC_PRODUCT_SWITCH_STATUS(HttpStatus.OK.value(),"상품 상태 변경 성공"),
    SUCC_PRODUCT_DELETE(HttpStatus.OK.value(),"상품 삭제 성공"),

    //상품 실패
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND.value(), "상품을 찾을 수 없습니다."),
    ;
    private int status;
    private String message;

}
