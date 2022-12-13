package com.programmers.jpapractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpapractice.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
