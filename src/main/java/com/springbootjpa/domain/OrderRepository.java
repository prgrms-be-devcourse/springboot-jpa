package com.springbootjpa.domain;

import com.springbootjpa.global.NoSuchEntityException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT o from Order o JOIN FETCH o.orderItems WHERE o.id =:id")
    Optional<Order> findById(Long id);

    default Order getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new NoSuchEntityException("존재하지 않는 주문입니다."));
    }
}
