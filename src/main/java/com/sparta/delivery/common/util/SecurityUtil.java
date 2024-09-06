package com.sparta.delivery.common.util;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
import com.sparta.delivery.help.entity.Help;
import com.sparta.delivery.help.repository.HelpRepository;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.product.entity.Product;
import com.sparta.delivery.product.repository.ProductRepository;
import com.sparta.delivery.review.entity.Review;
import com.sparta.delivery.review.repository.ReviewRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class SecurityUtil {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final HelpRepository helpRepository;

    // 주문 생성 시 orderType check
    public boolean checkOrderPermission(String orderType, Authentication authentication) {
        String userRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("NO_ROLE_FOUND");

        if (userRole.equals("ROLE_MANAGER") || userRole.equals("ROLE_MASTER"))
            return true;
        if ("delivery".equals(orderType) || "packaging".equals(orderType)) {
            return "ROLE_CUSTOMER".equals(userRole) || "ROLE_OWNER".equals(userRole) ;
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

    /**
     * 음식점의 사장이 맞는지 검사
     * @param authentication 사용자 정보를 담고 있는 객체
     * @param StoreId 음식점 식별자
     * @return 리소스의 접근을 false혹은 true로 반환합니다.
     */
    public boolean isStoreOwner(Authentication authentication, UUID StoreId) {
        log.info(" 음식점 사장이 맞는지 검사");

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));

        List<Store> storeList = storeRepository.findAllByUser(user);
        if(storeList.isEmpty()) throw new BusinessException(ResponseCode.NOT_FOUND_STORE);

        boolean result = storeList.stream()
                .anyMatch(store -> store.getId().equals(StoreId));

        log.info("완료: "+result);
        return result;
    }

    /**
     * 상품이 포함되어 있는 음식점의 사장인지 검사
     * @param authentication 사용자 정보를 담고 있는 객체
     * @param productId 상품 식별자
     * @return 리소스의 접근을 false혹은 true로 반환합니다.
     */
    public boolean isProductOwner(Authentication authentication, UUID productId) {
        log.info(" 상품이 포함되어 있는 음식점의 사장인지 검사");

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));

        List<Store> storeList = storeRepository.findAllByUser(user);
        if(storeList.isEmpty()) throw new BusinessException(ResponseCode.NOT_FOUND_STORE);

        Product product = productRepository.findById(productId)
                .orElseThrow(()->new BusinessException(ResponseCode.NOT_FOUND_PRODUCT));

        boolean result=storeList.stream()
                .anyMatch(store -> store.getId().equals(product.getStore().getId()));

        log.info("완료: "+result);
        return result;
    }

    /**
     *  리뷰 생성자가 맞는지 검사
     * @param authentication 사용자 정보를 담고 있는 auth 객체
     * @param reviewId 리뷰 식별자
     * @return 리소스의 접근을 false혹은 true로 반환합니다.
     */
    public boolean isReivewOwner(Authentication authentication, UUID reviewId) {
        log.info(" 리뷰 생성자가 맞는지 검사");

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ResponseCode.NOT_FOUND_USER));

        List<Review> reviewList = reviewRepository.findByUser(user);
        if(reviewList.isEmpty()) throw new BusinessException(ResponseCode.NOT_FOUND_REVIEW);

        boolean result = reviewList.stream()
                .anyMatch(review -> review.getId().equals(reviewId));

        log.info("완료: "+result);
        return result;

    }

    // 문의를 작성한 유저와 요청을 보낸 유저가 동일한지 확인
    public boolean checkHelpUser(UUID helpId, Authentication authentication) {
        String userRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("NO_ROLE_FOUND");

        if (userRole.equals("ROLE_MANAGER") || userRole.equals("ROLE_MASTER"))
            return true;

        Help help = helpRepository.findById(helpId).orElseThrow(() ->
                new BusinessException(ResponseCode.NOT_FOUND_HELP));
        UUID authenticatedUserId = ((UserDetailsImpl) authentication.getPrincipal()).getUserId();

        return authenticatedUserId.equals(help.getUser().getId());
    }
}
