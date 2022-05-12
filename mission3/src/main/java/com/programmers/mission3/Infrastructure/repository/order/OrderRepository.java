package com.programmers.mission3.Infrastructure.repository;

import com.programmers.mission3.Infrastructure.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
