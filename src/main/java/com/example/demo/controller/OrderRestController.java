package com.example.demo.controller;

import com.example.demo.converter.OrderConverter;
import com.example.demo.dto.OrderDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    @ExceptionHandler(value = {Exception.class})
    public ApiResponse<String> handleException(Exception e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Long> createOrder(@RequestBody OrderDto orderDto) {
        Long id = orderService.createOrder(OrderConverter.toOrder(orderDto));
        return ApiResponse.ok(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Page<OrderDto>> getAll(Pageable pageable) {
        Page<OrderDto> result = orderService.findAll(pageable);
        return ApiResponse.ok(result);
    }

    @GetMapping(value= "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<OrderDto> getOrder(@PathVariable long orderId) {
        OrderDto result = orderService.findById(orderId);
        return ApiResponse.ok(result);
    }

    @DeleteMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> deleteOrder(@PathVariable long orderId) {
        String result = orderService.deleteOrder(orderId);
        return ApiResponse.ok(result);
    }
}
