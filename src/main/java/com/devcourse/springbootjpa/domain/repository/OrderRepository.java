package com.devcourse.springbootjpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.springbootjpa.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
