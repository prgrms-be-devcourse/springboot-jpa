package com.prgrms.m3.repository;

import com.prgrms.m3.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where type(i) in (:type)")
    List<Item> findByType(String type);
}
