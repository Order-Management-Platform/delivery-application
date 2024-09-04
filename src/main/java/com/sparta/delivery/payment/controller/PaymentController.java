package com.sparta.delivery.payment.controller;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
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
    public ResponseEntity<ResponseDto> createPayment(@RequestBody PaymentRequest paymentRequest,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUserId();
        paymentGatewayService.paymentByCallback(userId, paymentRequest);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_PAYMENT_CREATE));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<?> getPayments(@PageableDefault(
            page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PaymentInfoResponse> payments = paymentService.getPayments(pageable);
        return ResponseEntity.ok(ResponsePageDto.of(ResponseCode.SUCC_PAYMENT_LIST_GET,payments));
    }


    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{paymentId}")
    public ResponseEntity<ResponseSingleDto<PaymentInfoResponse>> getPayment(@PathVariable("paymentId") UUID paymentId,
                                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUserId();
        PaymentInfoResponse paymentInfoResponse = paymentService.getPayment(userId, paymentId);
        return ResponseEntity.ok(ResponseSingleDto.of(ResponseCode.SUCC_PAYMENT_GET, paymentInfoResponse));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{paymentId}")
    public ResponseEntity<ResponseDto> cancelPayment(@PathVariable("paymentId") UUID paymentId,
                                                     @RequestBody CancelPaymentRequest cancelPaymentRequest) {
        paymentService.updatePayment(paymentId, cancelPaymentRequest);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_PAYMENT_CANCEL));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ResponseDto> deletePayment(@PathVariable("paymentId") UUID paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_PAYMENT_DELETE));
    }

}
