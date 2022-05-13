package com.example.demo.service;

import com.example.demo.converter.OrderConverter;
import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDto;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Long createOrder(Order order) {
        Order result =  orderRepository.save(order);
        return result.getOrderId();
    }

    @Transactional
    public Page<OrderDto> findAll(Pageable pageable) {
        Page<OrderDto> result =  orderRepository.findAll(pageable).map(OrderConverter::toOrderDto);
        return result;
    }

    @Transactional
    public OrderDto findById(long orderId) {
        OrderDto result = OrderConverter.toOrderDto(orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("There is no order with ID"+orderId)));
        return result;
    }

    @Transactional
    public String deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
        return "Successfully delete order" + orderId;
    }
}
