package com.blessing333.kdtjpa.domain.repository;

import com.blessing333.kdtjpa.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
