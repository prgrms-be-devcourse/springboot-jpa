package com.example.weeklyjpa.repository;

import com.example.weeklyjpa.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
