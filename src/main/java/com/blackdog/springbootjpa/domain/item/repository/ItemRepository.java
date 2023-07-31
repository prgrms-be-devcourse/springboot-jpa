package com.blackdog.springbootjpa.domain.item.repository;

import com.blackdog.springbootjpa.domain.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
