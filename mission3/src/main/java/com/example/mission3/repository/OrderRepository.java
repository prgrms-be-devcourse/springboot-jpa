package com.example.mission3.repository;

import com.example.mission3.domain.Order;
import com.example.mission3.domain.OrderItem;
import com.example.mission3.domain.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findOrdersByEmail(String email);

    List<Order> findOrderByOrderStatusAndEmailAndAddress(OrderStatus orderStatus, String email, String address);

}
