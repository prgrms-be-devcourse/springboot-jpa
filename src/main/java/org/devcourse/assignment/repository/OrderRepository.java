package org.devcourse.assignment.repository;

import org.devcourse.assignment.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
