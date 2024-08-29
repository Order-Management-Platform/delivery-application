package com.sparta.delivery.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //entity 존재여부
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "회원이 존재하지 않습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "카테고리가 존재하지 않습니다."),
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "결제내역이 존재하지 않습니다."),
    ORDER_NOU_FOUND(HttpStatus.BAD_REQUEST.value(), "주문내역이 존재하지 않습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),"상품이 존재하지 않습니다."),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),"상점이 존재하지 않습니다."),
    REGiON_NOT_FOUND(HttpStatus.BAD_REQUEST.value(),"지역이 존재하지 않습니다. "),

    //잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못 된 요청입니다.")



    ;





    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.message = message;
        this.status = status;
    }

}
