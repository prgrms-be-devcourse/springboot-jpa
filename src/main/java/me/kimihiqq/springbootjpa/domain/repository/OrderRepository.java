package me.kimihiqq.springbootjpa.domain.repository;

import me.kimihiqq.springbootjpa.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
