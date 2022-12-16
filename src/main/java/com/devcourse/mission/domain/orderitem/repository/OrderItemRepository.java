package com.devcourse.mission.domain.orderitem.repository;

import com.devcourse.mission.domain.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT oi FROM OrderItem oi WHERE EXISTS (SELECT i FROM Item i WHERE i.id = :itemId)")
    List<OrderItem> joinItemByItemId(@Param("itemId") long itemId);
}
