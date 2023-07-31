package com.blackdog.springbootjpa.domain.order.repository;

import com.blackdog.springbootjpa.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.item WHERE o.id = :orderId")
    Optional<Order> findOrderWithDetails(@Param("orderId") int orderId);
}
