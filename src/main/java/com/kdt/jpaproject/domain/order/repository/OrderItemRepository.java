package com.kdt.jpaproject.domain.order.repository;

import com.kdt.jpaproject.domain.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
