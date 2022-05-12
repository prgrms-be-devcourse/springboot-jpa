package com.kdt.jpaproject.domain.order.repository;

import com.kdt.jpaproject.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
