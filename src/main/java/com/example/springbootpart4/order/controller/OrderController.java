package com.example.springbootpart4.order.controller;

import com.example.springbootpart4.order.ApiResponse;
import com.example.springbootpart4.order.dto.OrderDto;
import com.example.springbootpart4.order.service.OrderService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalErrorHandler(Exception e)  {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/orders")
    public ApiResponse<String> save(@RequestBody OrderDto orderDto) {
        String save = orderService.save(orderDto);
        return ApiResponse.ok(save);
    }

    @GetMapping("/orders/{uuid}")
    public ApiResponse<OrderDto> getOne(@PathVariable String uuid) throws NotFoundException {
        OrderDto one = orderService.findOne(uuid);
        return ApiResponse.ok(one);
    }

    @GetMapping("/orders")
    public ApiResponse<Page<OrderDto>> getAll(Pageable pageable) {
        Page<OrderDto> all = orderService.findAll(pageable);
        return ApiResponse.ok(all);
    }

}
