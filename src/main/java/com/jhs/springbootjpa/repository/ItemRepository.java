package com.jhs.springbootjpa.repository;

import com.jhs.springbootjpa.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
