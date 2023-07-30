package com.jpaweekily.domain.order.application;

import com.jpaweekily.domain.order.dto.OrderCreateRequest;

public interface OrderService {
    Long createOrder(OrderCreateRequest request);
}
