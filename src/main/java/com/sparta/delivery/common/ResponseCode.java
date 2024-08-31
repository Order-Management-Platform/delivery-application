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
    SUCC_ORDER_STORE_LIST_GET(HttpStatus.OK.value(),"가게 주문 목록 조회 성공"),
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

    //리뷰 성공
    SUCC_REVIEW_CREAET(HttpStatus.OK.value(), "리뷰 생성 성공"),
    SUCC_REVIEW_USER_LIST_GET(HttpStatus.OK.value(), "리뷰 사용자 목록 조회 성공"),
    SUCC_REVIEW_LIST_GET(HttpStatus.OK.value(), "리뷰 목록 조회 성공"),
    SUCC_REVIEW_MODIFY(HttpStatus.OK.value(), "리뷰 수정 성공"),
    SUCC_REVIEW_DELETE(HttpStatus.OK.value(), "리뷰 삭제 성공"),
    SUCC_REVIEW_REPORT(HttpStatus.OK.value(), "리뷰 신고 성공"),


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

    // 고객센터 성공
    SUCC_HELP_CREATE(HttpStatus.OK.value(), "문의 요청 성공"),
    SUCC_HELP_GET(HttpStatus.OK.value(), "문의 전체 조회 성공"),
    SUCC_HELP_USER_GET(HttpStatus.OK.value(), "유저 문의 전체 조회 성공"),
    SUCC_HELP_UPDATE(HttpStatus.OK.value(), "문의 수정 성공"),
    SUCC_HELP_DELETE(HttpStatus.OK.value(), "문의 삭제 성공"),

    //entity 조회 실패
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "회원이 존재하지 않습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND.value(), "카테고리가 존재하지 않습니다."),
    NOT_FOUND_PAYMENT(HttpStatus.NOT_FOUND.value(), "결제내역이 존재하지 않습니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND.value(), "주문내역이 존재하지 않습니다."),
    NOT_FOUND_REGiON(HttpStatus.NOT_FOUND.value(),"지역이 존재하지 않습니다. "),
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND.value(), "음식점을 찾을 수 없습니다."),
    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND.value(), "상품을 찾을 수 없습니다."),
    NOT_FOUND_STORE_PRODUCT(HttpStatus.NOT_FOUND.value(), "가게에서 상품을 찾을 수 없습니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND.value(),"리뷰를 찾을 수 없습니다."),
    NOT_FOUND_NOTICE(HttpStatus.NOT_FOUND.value(), "공지사항이 존재하지 않습니다."),
    NOT_FOUND_HELP(HttpStatus.NOT_FOUND.value(), "문의내역을 찾을 수 없습니다."),

    // 주문한 지 5분이 지나면 취소 불가능
    ORDER_CANCEL_TIME_EXCEEDED(HttpStatus.REQUEST_TIMEOUT.value(), "주문 취소는 주문 후 5분 이내에만 가능합니다. 현재 주문은 취소할 수 없습니다."),

    //잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다."),

    // 잘못된 접근
    USER_DENIED_ACCESS_PAYMENT(HttpStatus.UNAUTHORIZED.value(),"본인의 결제내역만 조회할 수 있습니다."),
    STORE_OWNER_DENIED_ACCESS_PAYMENT(HttpStatus.UNAUTHORIZED.value(),"본인의 가게 결제내역만 조회할 수 있습니다."),

    //이메일 중복
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST.value(), "중복된 Email 입니다."),
    
    // jwt 관련 error
    NOT_MATCH_ROLE(HttpStatus.UNAUTHORIZED.value(), "JWT 권한과 실제 권한이 맞지 않습니다."),
    NOT_PRESENT_JWT(HttpStatus.UNAUTHORIZED.value(), "JWT 토큰이 존재하지 않습니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED.value(), "유효하지 않는 JWT 서명 입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED.value(), "만료된 JWT token 입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED.value(), "지원되지 않는 JWT 토큰 입니다."),
    EMPTY_JWT_CLAIMS(HttpStatus.UNAUTHORIZED.value(), "잘못된 JWT 토큰 입니다."),
    NOT_MATCH_LOGIN_INFO(HttpStatus.UNAUTHORIZED.value(), "email 또는 password 가 일치하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "해당 요청에 권한이 없습니다.");

    private final int status;
    private final String message;

}
