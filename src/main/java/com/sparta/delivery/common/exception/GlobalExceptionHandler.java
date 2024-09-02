package com.sparta.delivery.common.exception;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ErrorResponseDto;
import com.sparta.delivery.common.dto.ErrorsResponseDto;
import com.sparta.delivery.common.dto.FieldError;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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
    //삭제예정

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> badRequestExceptionHandler(CustomBadRequestException ex) {
        return ResponseEntity.badRequest().body(ErrorResponseDto.of(ex.getResponseCode()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(ex.getStatusCode()).body(ErrorsResponseDto.of(ResponseCode.BAD_REQUEST, FieldError.of(ex.getBindingResult())));
    }
}
