package com.sparta.delivery.payment.service;

import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.payment.dto.PaymentRequest;
import com.sparta.delivery.payment.dto.PgResponse;
import com.sparta.delivery.payment.entity.Payment;
import com.sparta.delivery.payment.entity.PaymentStatus;
import com.sparta.delivery.payment.repositoy.PaymentRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentGatewayServiceImpl implements PaymentGatewayService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public PgResponse paymentByCallback(UUID userId, PaymentRequest request) {
        // payment 조인 조회로 수정하기
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() -> new IllegalArgumentException("주문 내역이 없습니다."));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("회원정보를 찾지 못했습니다."));
        Payment payment = Payment.toEntity(user, 10000L);
        // order 에 payment 넣어주기

        String pgTransactionId = getPgTransactionId(request);

        PgResponse pgResponse = PgResponse.create(pgTransactionId);

        paymentRepository.save(payment);

        // 주문 실패
        if (pgResponse.getStatusCode() != 200) {
            //order.getPayment().changePaymentByFail(PaymentStatus.FAILED); custom exception 만들기
            throw new RuntimeException("결제 실패");
        }

        // 결제 상태 변경
        //order.getPayment().changePaymentBySuccess(PaymentStatus.COMPLETED, pgResponse.getPgTransactionId());

        return pgResponse;

    }


    private String getPgTransactionId(PaymentRequest request) {
        return request.getOrderId().toString().substring(0, 8) + "-"
                + request.getPaymentId().toString().substring(0, 8);
    }
}
