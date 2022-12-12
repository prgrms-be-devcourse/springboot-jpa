package com.prgrms.be.jpa.repository;

import com.prgrms.be.jpa.domain.order.Order;
import com.prgrms.be.jpa.domain.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);

    List<Order> findAllByOrderStatusOrderByOrderDatetime(OrderStatus orderStatus);

    List<Order> findByMemoContains(String memo);
}
