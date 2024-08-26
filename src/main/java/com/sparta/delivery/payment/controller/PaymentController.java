package com.sparta.delivery.payment.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.payment.dto.PaymentInfoResponse;
import com.sparta.delivery.payment.dto.PaymentRequest;
import com.sparta.delivery.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<ResponseDto> createPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.createPayment(paymentRequest);
        return ResponseEntity.ok(new ResponseDto(200, "payment successful"));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<?> getPayments(@PageableDefault(page = 0,
            size = 10,
            sort = "createAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PaymentInfoResponse> payments = paymentService.getPayments(pageable);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentInfoResponse> getPayment(@PathVariable("paymentId") UUID paymentId) {
        return null;
    }
}
