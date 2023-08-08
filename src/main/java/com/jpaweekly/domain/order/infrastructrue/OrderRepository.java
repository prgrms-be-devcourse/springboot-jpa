package com.jpaweekly.domain.order.infrastructrue;

import com.jpaweekly.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
