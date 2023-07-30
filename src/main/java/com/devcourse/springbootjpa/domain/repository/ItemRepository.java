package com.devcourse.springbootjpa.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.springbootjpa.domain.order.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
