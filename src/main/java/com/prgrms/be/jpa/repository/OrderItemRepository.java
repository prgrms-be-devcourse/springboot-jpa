package com.prgrms.be.jpa.repository;

import com.prgrms.be.jpa.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
