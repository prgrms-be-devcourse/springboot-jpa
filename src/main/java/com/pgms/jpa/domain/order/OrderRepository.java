package com.pgms.jpa.domain.order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Long save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    void deleteById(Long id);
}
