package org.devcourse.assignment.repository;

import org.devcourse.assignment.domain.order.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
