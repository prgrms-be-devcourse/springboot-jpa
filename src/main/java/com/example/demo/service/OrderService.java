package com.example.demo.service;

import com.example.demo.converter.OrderConverter;
import com.example.demo.domain.Order;
import com.example.demo.dto.OrderDto;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Long createOrder(Order order) {
        Order result =  orderRepository.save(order);
        return result.getOrderId();
    }

    public OrderDto findById(long orderId) {
        OrderDto result = OrderConverter.toOrderDto(orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("There is no order with ID "+orderId)));
        return result;
    }

    @Transactional
    public String deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
        return "Successfully delete order" + orderId;
    }
}
