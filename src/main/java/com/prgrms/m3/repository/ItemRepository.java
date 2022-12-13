package com.prgrms.m3.repository;

import com.prgrms.m3.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
