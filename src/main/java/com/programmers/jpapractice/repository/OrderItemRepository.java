package com.programmers.jpapractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpapractice.domain.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
