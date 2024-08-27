package com.sparta.delivery.order.service;

import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.order.dto.CreateOrderResponseDto;
import com.sparta.delivery.order.dto.OrderProductDto;
import com.sparta.delivery.order.dto.OrderRequestDto;
import com.sparta.delivery.order.dto.OrderResponseDto;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.product.entity.Product;
import com.sparta.delivery.product.repository.ProductRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    // 주문 생성 로직
    @Transactional
    public CreateOrderResponseDto createOrder(OrderRequestDto request) {
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() ->
                new NullPointerException("해당 가게를 찾을 수 없습니다."));
        Order createOrder = orderRepository.save(Order.create(request, store));

        for (OrderProductDto product : request.getProduct()) {
            addProductToOder(createOrder, request.getStoreId(), product.getProductId(), product.getAmount());
        }
        return CreateOrderResponseDto.of(200, "주문 생성 성공", createOrder.getId());
    }

    // 주문 생성 로직 시 상품 추가
    @Transactional
    public void addProductToOder(
            final Order order,
            final UUID storeId,
            final UUID productId,
            final int amount
    ) {
        productRepository.findAllByStoreId(storeId).stream()
                .filter(item -> Objects.equals(item.getId(), productId))
                .findAny()
                .ifPresent(product -> order.addProduct(create(order, productId, amount)));
    }

    // 주문 조회 로직
    public ResponsePageDto<OrderResponseDto> getOrder(int page, int size, String sort, boolean asc) {
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(direction, sort);
        Pageable pageable = PageRequest.of(page, size, sortBy);

        Page<Order> orderList = orderRepository.findAll(pageable);
        Page<OrderResponseDto> orderResponseDtoPage = orderList.map(order -> {
            UUID storeId = order.getStore().getId();
            // 상품의 가격을 가져와 OrderProductDto로 만들어주는 loop - kyeonkim
            List<OrderProductDto> products = order.getProductList().stream().map(orderProduct -> {
                UUID productId = orderProduct.getProductId();
                int amount = orderProduct.getAmount();

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + productId));

                int price = product.getPrice();

                return OrderProductDto.of(productId, price, amount);
            }).toList();
            int totalPrice = products.stream().mapToInt(product -> product.getPrice() * product.getAmount()).sum();
            String address = order.getStore().getAddress();

            return OrderResponseDto.of(order, storeId, products, totalPrice, address);
        });
        return ResponsePageDto.of(200, "주문 조회 성공", orderResponseDtoPage);
    }
}
