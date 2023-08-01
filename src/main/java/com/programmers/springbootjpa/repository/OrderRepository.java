package com.programmers.springbootjpa.repository;

import com.programmers.springbootjpa.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
