package com.blessing333.kdtjpa.domain.repository;

import com.blessing333.kdtjpa.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
