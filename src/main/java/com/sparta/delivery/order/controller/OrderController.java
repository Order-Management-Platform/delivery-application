package com.sparta.delivery.order.controller;

import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.common.dto.ResponseSingleDto;
import com.sparta.delivery.order.dto.CreateOrderResponseDto;
import com.sparta.delivery.order.dto.OrderRequestDto;
import com.sparta.delivery.order.dto.OrderResponseDto;
import com.sparta.delivery.order.dto.UpdateOrderRequestDto;
import com.sparta.delivery.order.service.OrderService;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    // 주문 생성
    @PreAuthorize("@securityUtilKyeonkim.checkOrderPermission(#orderRequest.orderType, authentication)")
    @PostMapping
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @RequestBody OrderRequestDto orderRequest,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest, ((UserDetailsImpl) userDetails).getUserId()));
    }

    // 주문 전체 조회
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping
    public ResponseEntity<ResponsePageDto<OrderResponseDto>> getOrder(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("asc") boolean asc
    ) {
        return ResponseEntity.ok(orderService.getOrder(page, size, sort, asc));
    }

    // 주문 유저 전체 조회
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/user")
    public ResponseEntity<ResponsePageDto<OrderResponseDto>> getUserOrder(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("asc") boolean asc,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(orderService.getUserOrder(page, size, sort, asc, ((UserDetailsImpl) userDetails).getUserId()));
    }

    // 주문 가게 전체 조회
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/store")
    public ResponseEntity<ResponsePageDto<OrderResponseDto>> getStoreOrder(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestParam("asc") boolean asc,
            @RequestParam("storeId") UUID storeId
    ) {
        return ResponseEntity.ok(orderService.getStoreOrder(page, size, sort, asc, storeId));
    }

    // 주문 단건 조회
    // 추가적으로 조회 요청을 보낸 사람이 해당 주문을 한게 맞는지 확인하는 작업이 필요 - kyeonkim
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseSingleDto<OrderResponseDto>> getFindByOrder(@PathVariable(name = "orderId") UUID orderId) {
        return ResponseEntity.ok(orderService.getFindByOrder(orderId));
    }

    // 주문 상태 수정
    @PreAuthorize("hasRole('OWNER')and @securityUtilKyeonkim.checkUpdateOrderOwnership(#request.orderId, authentication)")
    @PutMapping
    public ResponseEntity<ResponseDto> updateOrderStatus(@RequestBody UpdateOrderRequestDto request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(request));
    }

    // 주문 취소
    // 주문을 취소한 유저가 요청을 보낸 유저가 맞는 확인하는 로직 필요 - kyeonkim
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ResponseDto> cancelOrder(
            @PathVariable(name = "orderId") UUID orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId, userDetails.getUserId()));
    }
}
