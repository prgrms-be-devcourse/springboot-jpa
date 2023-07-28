package com.programmers.jpa.repository;

import com.programmers.jpa.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o join fetch o.member where o.id = :id")
    Optional<Order> findByIdWithMember(@Param("id") Long id);
}
