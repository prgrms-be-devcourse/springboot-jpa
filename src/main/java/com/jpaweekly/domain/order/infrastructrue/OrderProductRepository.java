package com.jpaweekly.domain.order.infrastructrue;

import com.jpaweekly.domain.order.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, CustomOrderProductRepository {
}
