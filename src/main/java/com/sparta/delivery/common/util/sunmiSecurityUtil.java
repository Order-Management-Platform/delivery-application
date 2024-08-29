package com.sparta.delivery.common.util;

import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.product.entity.Product;
import com.sparta.delivery.product.repository.ProductRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class sunmiSecurityUtil {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * 음식점의 사장이 맞는지 검사
     */
    public boolean isStoreOwner(Authentication authentication, UUID StoreId) {
        log.info(" 음식점 사장이 맞는지 검사");
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        List<Store> storeList = storeRepository.findAllByUser(user);

        boolean result=false;
        for (Store store : storeList) {
            if(store.getId().equals(StoreId)) result = true;
        }
        log.info("완료"+result);
        return result;
    }

    /**
     * 상품이 포함되어 있는 음식점의 사장인지 검사
     */
    public boolean isProductOwner(Authentication authentication, UUID id) {
        log.info(" 상품이 포함되어 있는 음식점의 사장인지 검사");

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));

        List<Store> storeList = storeRepository.findAllByUser(user);

        Product product = productRepository.findById(id)
                .orElseThrow(()->new NotFoundException(ResponseCode.NOT_FOUND_PRODUCT.getMessage()));

        boolean result=false;
        for (Store store : storeList) {
            if(store.getId().equals(product.getStore())) result = true;
        }

        log.info("완료");
        return result;
    }

}
