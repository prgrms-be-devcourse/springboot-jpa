package com.programmers.springbootjpa.repository;

import com.programmers.springbootjpa.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
