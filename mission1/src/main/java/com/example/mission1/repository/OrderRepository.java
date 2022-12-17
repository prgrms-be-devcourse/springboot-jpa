package com.example.mission1.repository;

import com.example.mission1.domain.Order;
import com.example.mission1.domain.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, OrderId> {
}
