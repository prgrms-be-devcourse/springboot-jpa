package com.jpaweekly.domain.order.infrastructrue;

import com.jpaweekly.domain.order.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, CustomOrderProductRepository {

    Page<OrderProduct> findByOrderId(Long orderId, Pageable pageable);
}
