package com.example.jpaweekly.domain.order.repository;

import com.example.jpaweekly.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
