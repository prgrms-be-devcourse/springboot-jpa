package com.programmers.mission3.Infrastructure.repository;

import com.programmers.mission3.Infrastructure.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
