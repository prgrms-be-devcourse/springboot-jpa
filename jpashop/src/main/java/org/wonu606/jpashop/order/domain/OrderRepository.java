package org.wonu606.jpashop.order.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Override
    @NonNull
    @Query("SELECT o FROM Order o JOIN FETCH o.orderLineItems ol JOIN FETCH ol.item")
    List<Order> findAll();

    @Query("SELECT o FROM Order o JOIN FETCH o.orderLineItems ol JOIN FETCH ol.item WHERE o.id = :id")
    Optional<Order> findByIdWithOrderLineItemsAndItems(@Param("id") Long id);
}
