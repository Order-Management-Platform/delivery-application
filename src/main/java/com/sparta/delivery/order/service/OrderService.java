package com.sparta.delivery.order.service;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.common.exception.NotFoundException;
import com.sparta.delivery.order.dto.*;
import com.sparta.delivery.order.entity.Order;
import com.sparta.delivery.order.repository.OrderRepository;
import com.sparta.delivery.product.repository.ProductRepository;
import com.sparta.delivery.store.entity.Store;
import com.sparta.delivery.store.repository.StoreRepository;
import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public OrderService(
            final UserRepository userRepository,
            final OrderRepository orderRepository,
            final StoreRepository storeRepository,
            final ProductRepository productRepository
    ) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    // 주문 생성 로직
    @Transactional
    public CreateOrderResponseDto createOrder(OrderRequestDto request, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("해당 유저를 찾을 수 없습니다."));
        Store store = storeRepository.findById(request.getStoreId()).orElseThrow(() ->
                new NotFoundException("해당 가게를 찾을 수 없습니다."));
        Order createOrder = orderRepository.save(Order.create(request, user, store));

        for (OrderProductDto product : request.getProduct()) {
            addProductToOder(createOrder, request.getStoreId(), product.getProductId(), product.getAmount());
        }
        return CreateOrderResponseDto.of(200, "주문 생성 성공", createOrder.getId());
    }

    // 주문 생성 로직 시 상품 추가
    @Transactional
    protected void addProductToOder(
            final Order order,
            final UUID storeId,
            final UUID productId,
            final int amount
    ) {
        productRepository.findAllByStoreId(storeId).stream()
                .filter(item -> Objects.equals(item.getId(), productId))
                .findAny()
                .ifPresentOrElse(
                        product -> order.addProduct(create(order, productId, product.getPrice(), amount)),
                        () -> {
                            throw new NotFoundException("가게에서 상품을 찾을 수 없습니다. 상품 ID: " + productId);
                        }
                );
    }

    // 주문 전체 조회 로직
    public ResponsePageDto<OrderResponseDto> getOrder(int page, int size, String sort, boolean asc) {
        Pageable pageable = createCustomPageable(page, size, sort, asc);

        Page<Order> orderList = orderRepository.findAll(pageable);

        return ResponsePageDto.of(200, "전체 주문 조회 성공", createOrderResponseDtoList(orderList));
    }

    // 주문 유저 조회 로직
    public ResponsePageDto<OrderResponseDto> getUserOrder(int page, int size, String sort, boolean asc, UUID userId) {
        Pageable pageable = createCustomPageable(page, size, sort, asc);

        Page<Order> orderList = orderRepository.findAllByUserId(userId, pageable);

        return ResponsePageDto.of(200, "주문 유저 조회 성공", createOrderResponseDtoList(orderList));
    }

    // 주문 가게 조회 로직
    public ResponsePageDto<OrderResponseDto> getStoreOrder(int page, int size, String sort, boolean asc, UUID storeId) {
        Pageable pageable = createCustomPageable(page, size, sort, asc);

        Page<Order> orderList = orderRepository.findAllByStoreId(storeId, pageable);

        return ResponsePageDto.of(200, "주문 가게 조회 성공", createOrderResponseDtoList(orderList));
    }

    // 주문 조회 시 Pageable를 만드는 로직
    protected Pageable createCustomPageable(int page, int size, String sort, boolean asc) {
        Sort.Direction direction = asc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sortBy = Sort.by(direction, sort);
        return PageRequest.of(page, size, sortBy);
    }

    // 주문 조회 시 Page<OrderResponseDto>를 만드는 로직
    protected Page<OrderResponseDto> createOrderResponseDtoList(Page<Order> orderList) {
        Page<OrderResponseDto> orderResponseDtoPage = orderList.map(order -> {
            List<OrderProductDto> products = order.getProductList().stream().map(orderProduct ->
                    OrderProductDto.of(orderProduct.getProductId(), orderProduct.getAmount(), orderProduct.getPrice())
            ).toList();
            int totalPrice = products.stream().mapToInt(product -> product.getPrice() * product.getAmount()).sum();

            return OrderResponseDto.of(order, products, totalPrice);
        });
        return orderResponseDtoPage;
    }


    // 주문 단건 조회 로직
    public ResponseSingleDto<OrderResponseDto> getFindByOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundException("해당 주문을 찾을 수 없습니다."));
        List<OrderProductDto> products = order.getProductList().stream().map(orderProduct ->
                OrderProductDto.of(orderProduct.getProductId(), orderProduct.getAmount(), orderProduct.getPrice())
        ).toList();
        int totalPrice = products.stream().mapToInt(product -> product.getPrice() * product.getAmount()).sum();
        return ResponseSingleDto.of(200, "주문 단건 조회 성공",
                OrderResponseDto.of(order, products, totalPrice));
    }

    // 주문 상태 수정 로직
    @Transactional
    public ResponseDto updateOrderStatus(UpdateOrderRequestDto request) {
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(() ->
                new NotFoundException("해당 주문을 찾을 수 없습니다."));
        order.updateStatus(request.getOrderStatus());
        return ResponseDto.of(200, "주문 상태 수정 성공");
    }

    // 주문 취소 로직
    @Transactional
    public ResponseDto cancelOrder(UUID orderId, UUID userId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundException("해당 주문을 찾을 수 없습니다."));
        order.cancel(userId);
        order.updateStatus("주문 취소");
        return ResponseDto.of(200, "주문 취소 성공");
    }


}
