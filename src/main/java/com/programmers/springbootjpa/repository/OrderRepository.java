package com.programmers.springbootjpa.repository;

import com.programmers.springbootjpa.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
