package com.programmers.jpapractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpapractice.domain.order.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
