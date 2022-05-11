package com.example.springbootjpa.repository;

import com.example.springbootjpa.domain.order.Item;
import com.example.springbootjpa.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
