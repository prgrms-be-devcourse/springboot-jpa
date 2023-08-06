package com.programmers.jpa.item.infra;

import com.programmers.jpa.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {
}
