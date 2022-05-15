package com.blessing333.kdtjpa.domain.repository;

import com.blessing333.kdtjpa.domain.OrderItem;
import com.blessing333.kdtjpa.domain.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
}
