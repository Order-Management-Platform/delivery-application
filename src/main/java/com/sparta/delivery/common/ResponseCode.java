package com.sparta.delivery.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    // 지역 성공
    SUCC_REGION_CREATE(HttpStatus.OK.value(),"지역 생성 성공"),
    SUCC_REGION_GET(HttpStatus.OK.value(),"지역 조회 성공"),
    SUCC_REGION_UPDATE(HttpStatus.OK.value(),"지역 수정 성공"),
    SUCC_REGION_DELETE(HttpStatus.OK.value(),"지역 삭제 성공"),

    // 주문 성공
    SUCC_ORDER_CREATE(HttpStatus.OK.value(),"주문 생성 성공"),
    SUCC_ORDER_LIST_GET(HttpStatus.OK.value(),"전제 주문 목록 조회 성공"),
    SUCC_ORDER_USER_LIST_GET(HttpStatus.OK.value(),"유저 주문 목록 조회 성공"),
    SUCC_ORDER_STORE_LIST_GET(HttpStatus.OK.value(),"유저 주문 목록 조회 성공"),
    SUCC_ORDER_SINGLE_GET(HttpStatus.OK.value(),"주문 단건 조회 성공"),
    SUCC_ORDER_UPDATE_STATUS(HttpStatus.OK.value(),"주문 상태 변경 성공"),
    SUCC_ORDER_CANCLE(HttpStatus.OK.value(),"주문 취소 성공"),

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

    //유저 성공
    SUCC_USER_CREATE(HttpStatus.OK.value(),"회원 생성 성공"),
    SUCC_USER_LIST_GET(HttpStatus.OK.value(),"회원 목록 조회 성공"),
    SUCC_USER_GET(HttpStatus.OK.value(),"회원정보 조회 성공"),
    SUCC_USER_MODIFY(HttpStatus.OK.value(),"회원정보 수정 성공"),
    SUCC_USER_DELETE(HttpStatus.OK.value(),"회원 삭제 성공"),

    //결제 성공
    SUCC_PAYMENT_CREATE(HttpStatus.OK.value(),"결제 성공"),
    SUCC_PAYMENT_LIST_GET(HttpStatus.OK.value(),"결제 목록 조회 성공"),
    SUCC_PAYMENT_GET(HttpStatus.OK.value(),"결제 조회 성공"),
    SUCC_PAYMENT_CANCEL(HttpStatus.OK.value(),"결제 취소 성공"),
    SUCC_PAYMENT_DELETE(HttpStatus.OK.value(),"결제 삭제 성공"),

    //카테고리 성공
    SUCC_CATEGORY_CREATE(HttpStatus.OK.value(),"카테고리 생성 성공"),
    SUCC_CATEGORY_LIST_GET(HttpStatus.OK.value(),"카테고리 목록 조회 성공"),
    SUCC_CATEGORY_MODIFY(HttpStatus.OK.value(),"카테고리 취소 성공"),
    SUCC_CATEGORY_DELETE(HttpStatus.OK.value(),"카테고리 삭제 성공"),


    //ai 성공
    SUCC_AI_GET(HttpStatus.OK.value(), "ai 음식 설명 요청 성공"),

    //notice 성공
    SUCC_NOTICE_CREATE(HttpStatus.OK.value(), "공지사항 생성 성공"),
    SUCC_NOTICE_GET(HttpStatus.OK.value(), "공지사항 상세 조회 성공"),
    SUCC_NOTICE_LIST_GET(HttpStatus.OK.value(), "공지사항 목록 조회 성공"),
    SUCC_NOTICE_MODIFY(HttpStatus.OK.value(), "공지사항 수정 성공"),
    SUCC_NOTICE_DELETE(HttpStatus.OK.value(), "공지사항 삭제 성공"),


    //entity 조회 실패
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "회원이 존재하지 않습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND.value(), "카테고리가 존재하지 않습니다."),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND.value(), "결제내역이 존재하지 않습니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND.value(), "주문내역이 존재하지 않습니다."),
    NOT_FOUND_REGiON(HttpStatus.NOT_FOUND.value(),"지역이 존재하지 않습니다. "),
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND.value(), "음식점을 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND.value(), "상품을 찾을 수 없습니다."),
    NOT_FOUND_STORE_PRODUCT(HttpStatus.NOT_FOUND.value(), "가게에서 상품을 찾을 수 없습니다."),
    NOT_FOUND_NOTICE(HttpStatus.NOT_FOUND.value(), "공지사항이 존재하지 않습니다."),

    //잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),


    // 잘못된 접근
    USER_DENIED_ACCESS_PAYMENT(HttpStatus.UNAUTHORIZED.value(),"본인의 결제내역만 조회할 수 있습니다."),
    STORE_OWNER_DENIED_ACCESS_PAYMENT(HttpStatus.UNAUTHORIZED.value(),"본인의 가게 결제내역만 조회할 수 있습니다.");


    private final int status;
    private final String message;

}
