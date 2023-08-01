package com.programmers.springbootjpa.repository;

import com.programmers.springbootjpa.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
