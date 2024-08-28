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



    //상품 실패
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND.value(), "상품을 찾을 수 없습니다."),
    //상품 실패
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND.value(), "음식점을 찾을 수 없습니다."),
    ;

    private int status;
    private String message;

}
