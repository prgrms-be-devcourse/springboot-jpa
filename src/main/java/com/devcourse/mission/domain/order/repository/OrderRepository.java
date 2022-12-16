package com.devcourse.mission.domain.order.repository;

import com.devcourse.mission.domain.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.id = :id")
    @EntityGraph(attributePaths = {"customer", "orderItems", "orderItems.item"})
    Order joinCustomerOrderItemByOrderId(@Param("id") long id);

    @Query("select o from Order o where o.id = :id")
    @EntityGraph(attributePaths = {"orderItems"})
    Order joinOrderItemByOrderId(@Param("id") long id);
}
