package com.prgrms.be.jpa.repository;

import com.prgrms.be.jpa.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
