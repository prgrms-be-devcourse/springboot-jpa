package com.jpaweekily.domain.order.infrastructrue;

import com.jpaweekily.domain.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
