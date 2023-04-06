package com.programmers.kwonjoosung.springbootjpa.repository;

import com.programmers.kwonjoosung.springbootjpa.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}

