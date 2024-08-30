package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.dto.ErrorResponseDto;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundExceptionHandler(NotFoundException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(ex.getResponseCode()));
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> accessDeniedExceptionHandler(CustomAccessDeniedException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(ex.getResponseCode()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> badRequestExceptionHandler(CustomBadRequestException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(ex.getResponseCode()));
    }
}
