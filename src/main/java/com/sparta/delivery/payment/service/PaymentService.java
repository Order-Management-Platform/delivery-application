package com.sparta.delivery.payment.service;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.payment.dto.CancelPaymentRequest;
import com.sparta.delivery.payment.dto.PaymentInfoResponse;
import com.sparta.delivery.payment.entity.Payment;
import com.sparta.delivery.payment.repositoy.PaymentRepository;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    public Page<PaymentInfoResponse> getPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(PaymentInfoResponse::of);
    }

    public PaymentInfoResponse getPayment(UUID userId, UUID paymentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_PAYMENT));
        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_ORDER));

        if (user.getRole() == UserRole.CUSTOMER) {
            if (!payment.getUser().equals(user)) {
                throw new BusinessException(ResponseCode.USER_DENIED_ACCESS_PAYMENT);
            }
        }

        if (user.getRole() == UserRole.OWNER) {
            if (!isMatchStore(user, order)) {
                throw new BusinessException(ResponseCode.STORE_OWNER_DENIED_ACCESS_PAYMENT);
            }
        }

        return paymentRepository.findById(paymentId)
                .map(PaymentInfoResponse::of)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_PAYMENT));
    }


    @Transactional
    public void updatePayment(UUID paymentId, CancelPaymentRequest cancelPaymentRequest) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_PAYMENT));
        payment.changePaymentByCancel(cancelPaymentRequest.getPaymentStatus());
    }


    @Transactional
    public void deletePayment(UUID paymentId, UUID userId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_PAYMENT));
        payment.delete(userId);
    }

    private boolean isMatchStore(User user, Order order) {
        return storeRepository.findAllByUser(user).stream().anyMatch(store -> store.equals(order.getStore()));
    }
}
