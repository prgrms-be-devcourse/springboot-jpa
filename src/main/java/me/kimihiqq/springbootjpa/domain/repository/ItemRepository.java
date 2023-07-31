package me.kimihiqq.springbootjpa.domain.repository;

import me.kimihiqq.springbootjpa.domain.order.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
