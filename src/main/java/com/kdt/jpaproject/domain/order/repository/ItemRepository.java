package com.kdt.jpaproject.domain.order.repository;

import com.kdt.jpaproject.domain.order.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
