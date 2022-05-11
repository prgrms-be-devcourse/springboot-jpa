package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.order.Member;
import com.example.springbootjpa.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
