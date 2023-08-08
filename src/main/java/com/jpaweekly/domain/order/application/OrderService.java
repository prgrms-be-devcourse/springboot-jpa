package com.jpaweekly.domain.order.application;

import com.jpaweekly.domain.order.dto.OrderCreateRequest;
import com.jpaweekly.domain.order.dto.OrderProductResponse;
import com.jpaweekly.domain.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Long createOrder(Long id, OrderCreateRequest request);

    OrderResponse findOrderById(Long id);

    Page<OrderResponse> findOrders(Pageable pageable);

    Page<OrderProductResponse> findOrderProducts(Pageable pageable);

    Page<OrderProductResponse> findOrderProductsByOderId(Long orderId, Pageable pageable);

    void deliverOrder(Long id);

    void completeOrder(Long id);

    void deleteOrder(Long id);

    void cancelOrder(Long id);
}
