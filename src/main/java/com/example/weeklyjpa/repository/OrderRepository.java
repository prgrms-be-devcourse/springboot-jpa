package com.example.weeklyjpa.repository;

import com.example.weeklyjpa.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order,Long> {

}
