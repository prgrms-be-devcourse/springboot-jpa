package com.kdt.jpa.domain.order.controller;

import com.kdt.jpa.domain.ApiResponse;
import com.kdt.jpa.domain.order.Order;
import com.kdt.jpa.domain.order.dto.OrderDto;
import com.kdt.jpa.domain.order.service.OrderService;
import javassist.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler (Exception e) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @PostMapping("")
    public ApiResponse<String> save(@RequestBody OrderDto orderDto) {
        String id = orderService.save(orderDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("")
    public ApiResponse<Page<OrderDto>> getAll(Pageable pageable) {
        Page<OrderDto> all = orderService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @GetMapping("{orderId}")
    public ApiResponse<OrderDto> getOne(@PathVariable String orderId) {
        OrderDto one = orderService.findOne(orderId);
        return ApiResponse.ok(one);
    }

}
