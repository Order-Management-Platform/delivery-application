package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundExceptionHandler(NotFoundException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(ex.getMessage()));
    }


}
