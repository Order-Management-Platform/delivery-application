package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //삭제예정
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundExceptionHandler(NotFoundException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(ex.getResponseCode()));
    }
    //삭제예정
    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> accessDeniedExceptionHandler(CustomAccessDeniedException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(ex.getResponseCode()));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> businessExceptionHandler(BusinessException ex) {
        return ResponseEntity.status(ex.getResponseCode().getStatus()).body(ErrorResponseDto.of(ex.getResponseCode()));
    }
}
