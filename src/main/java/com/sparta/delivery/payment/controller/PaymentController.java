package com.sparta.delivery.payment.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.payment.dto.CancelPaymentRequest;
import com.sparta.delivery.payment.dto.PaymentRequest;
import com.sparta.delivery.payment.dto.PaymentInfoResponse;
import com.sparta.delivery.payment.service.PaymentGatewayService;
import com.sparta.delivery.payment.service.PaymentService;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentGatewayService paymentGatewayService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping //결제 요청
    public ResponseEntity<ResponseDto> createPayment(@RequestBody PaymentRequest paymentCallbackRequest,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUserId();
        paymentGatewayService.paymentByCallback(userId, paymentCallbackRequest);
        return ResponseEntity.ok(ResponseDto.of(200, "payment successful"));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<?> getPayments(@PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PaymentInfoResponse> payments = paymentService.getPayments(pageable);
        return ResponseEntity.ok(ResponsePageDto.of(200,"조회 성공",payments));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentInfoResponse> getPayment(@PathVariable("paymentId") UUID paymentId) {
        PaymentInfoResponse paymentInfoResponse = paymentService.getPayment(paymentId);
        return ResponseEntity.ok(paymentInfoResponse);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{paymentId}")
    public ResponseEntity<ResponseDto> cancelPayment(@PathVariable("paymentId") UUID paymentId,
                                                     @RequestBody CancelPaymentRequest cancelPaymentRequest) {
        paymentService.updatePayment(paymentId, cancelPaymentRequest);
        return ResponseEntity.ok(ResponseDto.of(200, "결제가 취소 되었습니다."));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ResponseDto> deletePayment(@PathVariable("paymentId") UUID paymentId,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUserId();
        paymentService.deletePayment(paymentId, userId);
        return ResponseEntity.ok(ResponseDto.of(200, "결제가 삭제 되었습니다."));
    }

}
