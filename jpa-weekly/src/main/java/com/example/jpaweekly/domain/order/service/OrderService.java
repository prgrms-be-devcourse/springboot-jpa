package com.example.jpaweekly.domain.order.service;

import com.example.jpaweekly.domain.order.dto.OrderCreateRequest;
import com.example.jpaweekly.domain.order.dto.OrderResponse;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    Long createOrder(OrderCreateRequest request, Long userId);
}
