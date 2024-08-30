package com.sparta.delivery.payment.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.entity.OrderProduct;
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
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_ORDER));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));


        Long totalPrice = order.getProductList().stream().mapToLong(OrderProduct::getPrice).sum();


        Payment payment = Payment.create(user, order.getId(), totalPrice);
        order.addPayment(payment);

        Payment savedPayment = paymentRepository.save(payment);
        String pgTransactionId = getPgTransactionId(order.getId(), savedPayment.getId());

        PgResponse pgResponse = PgResponse.create(pgTransactionId);

        // 주문 실패
        if (pgResponse.getStatusCode() != 200) {
            order.paymentFailed();
            payment.changePaymentByFail(PaymentStatus.FAILED); //custom exception 만들기
            throw new RuntimeException("결제 실패");
        }

        // 결제 상태 변경
        order.paymentCompleted();
        payment.changePaymentBySuccess(PaymentStatus.COMPLETED, pgResponse.getPgTransactionId());

        return pgResponse;

    }

    @Override
    public void cancelPayment(UUID orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_PAYMENT));
        String pgTransactionId = payment.getPgTransactionId();
        try {
            // 외부결제 취소 로직
            payment.changePaymentByCancel(PaymentStatus.CANCELED);
        } catch (RuntimeException e) {
            throw new BusinessException(ResponseCode.BAD_REQUEST);
        }

    }

    private String getPgTransactionId(UUID orderId, UUID paymentId) {
        return orderId.toString().substring(0, 8) + "-"
                + paymentId.toString().substring(0, 8);
    }
}
