package com.programmers.jpa.repository;

import com.programmers.jpa.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("select oi from OrderItem oi" +
            " join fetch oi.item i" +
            " join fetch oi.order o" +
            " join fetch o.member m" +
            " where oi.id = :id")
    Optional<OrderItem> findByIdWithOthers(@Param("id") Long id);
}
