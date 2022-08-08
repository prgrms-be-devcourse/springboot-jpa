package com.kdt.lecture.Repository;

import com.kdt.lecture.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
