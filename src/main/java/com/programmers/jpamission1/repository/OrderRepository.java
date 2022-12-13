package com.programmers.jpamission1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpamission1.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
}
