package com.example.demo.controller;

import com.example.demo.converter.OrderConverter;
import com.example.demo.dto.OrderDto;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object createOrder(@RequestBody @Valid OrderDto orderDto) {
        Long id = orderService.createOrder(OrderConverter.toOrder(orderDto));
        return id;
    }

    @GetMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getOrder(@PathVariable long id) {
        OrderDto result = orderService.findById(id);
        return result;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object deleteOrder(@PathVariable long id) {
        String result = orderService.deleteOrder(id);
        return result;
    }
}
