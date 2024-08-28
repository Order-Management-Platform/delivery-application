package com.sparta.delivery.order.service;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.order.dto.*;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
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
                .ifPresent(product -> order.addProduct(create(order, productId, product.getPrice(), amount)));
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
            List<OrderProductDto> products = order.getProductList().stream().map(orderProduct ->
                    OrderProductDto.of(orderProduct.getProductId(), orderProduct.getAmount(), orderProduct.getPrice())
            ).toList();
            int totalPrice = products.stream().mapToInt(product -> product.getPrice() * product.getAmount()).sum();
            String address = order.getStore().getAddress();

            return OrderResponseDto.of(order, storeId, products, totalPrice, address);
        });
        return ResponsePageDto.of(200, "주문 조회 성공", orderResponseDtoPage);
    }

    // 주문 단건 조회 로직
    public ResponseSingleDto<OrderResponseDto> getFindByOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        Store store = storeRepository.findById(order.getStore().getId()).orElseThrow(() ->
                new NullPointerException("해당 가게를 찾을 수 없습니다."));
        List<OrderProductDto> products = order.getProductList().stream().map(orderProduct ->
                OrderProductDto.of(orderProduct.getProductId(), orderProduct.getAmount(), orderProduct.getPrice())
        ).toList();
        int totalPrice = products.stream().mapToInt(product -> product.getPrice() * product.getAmount()).sum();
        return ResponseSingleDto.of(200, "주문 단건 조회 성공",
                OrderResponseDto.of(order, store.getId(), products, totalPrice, store.getAddress()));
    }

    // 주문 상태 수정 로직
    @Transactional
    public ResponseDto updateOrderStatus(UpdateOrderRequestDto request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        order.updateStatus(request.getOrderStatus());
        return ResponseDto.of(200, "주문 상태 수정 성공");
    }

    // 주문 취소 로직
    @Transactional
    public ResponseDto cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("해당 주문을 찾을 수 없습니다."));
        order.cancel(orderId); // 유저 아이디로 변경해야함 - kyeonkim
        order.updateStatus("주문 취소"); // 상태값을 여기서 바꾸는게 맞는지 의문 - kyeonkim
        return ResponseDto.of(200, "주문 취소 성공");
    }
}
