package com.sparta.delivery.common.util;

import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityUtilKyeonkim {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    public SecurityUtilKyeonkim(final OrderRepository orderRepository, final StoreRepository storeRepository) {
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
    }

    // 주문 생성 시 orderType check
    public boolean checkOrderPermission(String orderType, Authentication authentication) {
        String userRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("NO_ROLE_FOUND");

        if (userRole.equals("ROLE_MANAGER") || userRole.equals("ROLE_MASTER"))
            return true;
        if ("delivery".equals(orderType) || "packaging".equals(orderType)) {
            return "ROLE_CUSTOMER".equals(userRole);
        } else if ("internal".equals(orderType)) {
            return "ROLE_OWNER".equals(userRole);
        }

        return false;
    }

    // 주문 수정 시 요청한 유저가 가게 주인이 맞는지 확인
    public boolean checkUpdateOrderOwnership(UUID orderId, Authentication authentication) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return false;
        }

        Order order = optionalOrder.get();
        UUID storeId = order.getStore().getId();

        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isEmpty()) {
            return false;
        }

        Store store = optionalStore.get();
        UUID authenticatedUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();

        return authenticatedUserId.equals(store.getUser().getId());
    }

    // 주문 요청의 id와 요청을 보낸 유저가 동일한지 확인
    public boolean checkOrderOwnership(UUID orderId, Authentication authentication) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return false;
        }

        Order order = optionalOrder.get();
        UUID authenticatedUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();

        return authenticatedUserId.equals(order.getUser().getId());
    }

    // 주문 가게 조회 요청 시 요청을 보낸 유저가 가게 주인이 맞는지 확인
    public boolean checkStoreOwnership(UUID storeId, Authentication authentication) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isEmpty()) {
            return false;
        }

        Store store = optionalStore.get();
        UUID authenticatedUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();

        return authenticatedUserId.equals(store.getUser().getId());
    }

}
