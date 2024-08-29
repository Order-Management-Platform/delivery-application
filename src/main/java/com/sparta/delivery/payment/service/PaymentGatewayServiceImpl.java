package com.sparta.delivery.payment.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.NotFoundException;
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
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_ORDER));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.NOT_FOUND_USER));

        Payment payment = Payment.create(user, 10000L);
        order.addPayment(payment);

        Payment savedPayment = paymentRepository.save(payment);
        String pgTransactionId = getPgTransactionId(order.getId(), savedPayment.getId());

        PgResponse pgResponse = PgResponse.create(pgTransactionId);

        // 주문 실패
        if (pgResponse.getStatusCode() != 200) {
            //주문상태 정해야할듯 - dowon
            order.updateStatus("결제실패?");
            payment.changePaymentByFail(PaymentStatus.FAILED); //custom exception 만들기
            throw new RuntimeException("결제 실패");
        }

        // 결제 상태 변경
        order.updateStatus("주문확인중?");
        payment.changePaymentBySuccess(PaymentStatus.COMPLETED, pgResponse.getPgTransactionId());

        return pgResponse;

    }


    private String getPgTransactionId(UUID orderId, UUID paymentId) {
        return orderId.toString().substring(0, 8) + "-"
                + paymentId.toString().substring(0, 8);
    }
}
