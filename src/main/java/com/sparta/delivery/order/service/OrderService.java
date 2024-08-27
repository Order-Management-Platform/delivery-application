package com.sparta.delivery.order.service;

import com.sparta.delivery.order.dto.OrderProductRequestDto;
import com.sparta.delivery.order.dto.OrderRequestDto;
import com.sparta.delivery.order.dto.OrderResponseDto;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.product.repository.ProductRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.sparta.delivery.order.entity.OrderProduct.create;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public OrderService(
            final OrderRepository orderRepository,
            final StoreRepository storeRepository,
            final ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() ->
                new NullPointerException("해당 가게를 찾을 수 없습니다."));
        Order createOrder = orderRepository.save(Order.create(request, store));

        for (OrderProductRequestDto product : request.getProduct()) {
            addProductToOder(createOrder, request.getStoreId(), product.getProductId());
        }
        return OrderResponseDto.of(200, "주문 생성 성공", createOrder.getId());
    }

    @Transactional
    public void addProductToOder(final Order order, final UUID storeId, final UUID productId) {
        productRepository.findAllByStoreId(storeId).stream()
                .filter(item -> Objects.equals(item.getId(), productId))
                .findAny()
                .ifPresent(product -> order.addProduct(create(order, productId)));
    }
}
