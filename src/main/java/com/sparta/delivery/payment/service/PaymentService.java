package com.sparta.delivery.payment.service;

import com.sparta.delivery.payment.dto.PaymentInfoResponse;
import com.sparta.delivery.payment.dto.PaymentRequest;
import com.sparta.delivery.payment.entity.Payment;
import com.sparta.delivery.payment.repositoy.PaymentRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
//    private final OrderRepository orderRepository;


    public void createPayment(PaymentRequest paymentRequest) {
        //
        User user = userRepository.findById(paymentRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
        // order Entity 추가후 주석 제거
//        Order order = orderRepository.findById(paymentRequest.getOrderId())
//                .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));
        Payment payment = Payment.toEntity(paymentRequest, user/*, order*/);
        paymentRepository.save(payment);
    }


    public Page<PaymentInfoResponse> getPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(PaymentInfoResponse::of);
    }
}
