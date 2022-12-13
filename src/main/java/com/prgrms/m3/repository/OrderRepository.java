package com.prgrms.m3.repository;

import com.prgrms.m3.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
