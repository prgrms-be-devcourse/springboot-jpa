package com.devcourse.springbootjpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.springbootjpa.domain.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
