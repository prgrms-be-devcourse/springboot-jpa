package com.jpaweekly.domain.order.application;

import com.jpaweekly.domain.order.dto.OrderCreateRequest;

public interface OrderService {
    Long createOrder(Long id, OrderCreateRequest request);
}
