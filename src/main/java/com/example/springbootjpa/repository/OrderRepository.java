package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.Customer;
import com.example.springbootjpa.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
