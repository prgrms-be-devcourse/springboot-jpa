package com.jpaweekily.domain.order.repository;

import com.jpaweekily.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
