package com.example.jpaweekly.domain.order.repository;

import com.example.jpaweekly.domain.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
