package com.programmers.june.jpastudy.domain.order.repository;

import com.programmers.june.jpastudy.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
