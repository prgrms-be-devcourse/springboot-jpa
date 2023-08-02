package com.programmers.june.jpastudy.domain.item.repository;

import com.programmers.june.jpastudy.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
