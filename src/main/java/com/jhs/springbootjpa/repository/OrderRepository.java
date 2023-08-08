package com.jhs.springbootjpa.repository;

import com.jhs.springbootjpa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
