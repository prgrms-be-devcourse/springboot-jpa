package com.devcourse.mission.domain.item.repository;

import com.devcourse.mission.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
