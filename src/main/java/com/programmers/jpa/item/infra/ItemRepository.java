package com.programmers.jpa.item.infra;

import com.programmers.jpa.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository<T extends Item> extends JpaRepository<T, Long> {
    @Query(value = "SELECT * FROM item WHERE item_type = :itemType", nativeQuery = true)
    List<T> findAllByItemType(@Param("itemType") String itemType);
}
