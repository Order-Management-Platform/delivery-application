package com.sparta.delivery.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    //상품 성공
    SUCC_PRODUCT_CREATE(HttpStatus.OK.value(),"상품 생성 성공"),
    SUCC_PRODUCT_LIST_GET(HttpStatus.OK.value(),"상품 목록 조회 성공"),
    SUCC_PRODUCT_GET(HttpStatus.OK.value(),"상품 상세 조회 성공"),
    SUCC_PRODUCT_MODIFY(HttpStatus.OK.value(), "상품 수정 성공"),
    SUCC_PRODUCT_SWITCH_STATUS(HttpStatus.OK.value(),"상품 상태 변경 성공"),
    SUCC_PRODUCT_DELETE(HttpStatus.OK.value(),"상품 삭제 성공"),

    //음식점 성공
    SUCC_STORE_CREATE(HttpStatus.OK.value(),"음식점 생성 성공"),
    SUCC_STORE_LIST_GET(HttpStatus.OK.value(),"음식점 목록 조회 성공"),
    SUCC_STORE_OWNER_LIST_GET(HttpStatus.OK.value(),"사장님 음식점 목록 조회 성공"),
    SUCC_STORE_GET(HttpStatus.OK.value(),"음식점 상세 조회 성공"),
    SUCC_STORE_MODIFY(HttpStatus.OK.value(),"음식점 수정 성공"),
    SUCC_STORE_DELETE(HttpStatus.OK.value(),"음식점 삭제 성공"),

    //ai 성공
    SUCC_AI_GET(HttpStatus.OK.value(), "ai 음식 설명 요청 성공"),

    //상품 실패
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND.value(), "상품을 찾을 수 없습니다."),
    //상품 실패
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND.value(), "음식점을 찾을 수 없습니다."),



    //entity 조회 실패
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

}
