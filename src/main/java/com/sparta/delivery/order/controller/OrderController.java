package com.sparta.delivery.order.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.order.dto.CreateOrderResponseDto;
import com.sparta.delivery.order.dto.OrderRequestDto;
import com.sparta.delivery.order.dto.OrderResponseDto;
import com.sparta.delivery.order.dto.UpdateOrderRequestDto;
import com.sparta.delivery.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 생성
    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    // 주문 조회
    @GetMapping
    public ResponseEntity<ResponsePageDto<OrderResponseDto>> getOrder(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("asc") boolean asc
    ) {
        return ResponseEntity.ok(orderService.getOrder(page, size, sort, asc));
    }

    // 주문 상태 수정
    @PutMapping
    public ResponseEntity<ResponseDto> updateOrderStatus(@RequestBody UpdateOrderRequestDto request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(request));
    }
}
